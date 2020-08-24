package com.whiz.app.boot.infrastructure.security;

import com.whiz.app.boot.domain.model.UserProfile;
import com.whiz.app.boot.domain.repository.UserProfileRepository;
import com.whiz.app.boot.infrastructure.UserNotActivatedException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Slf4j
@Component("userDetailsService")
public class UserModelDetailsService implements UserDetailsService {

    private final UserProfileRepository userProfileRepository;

    public UserModelDetailsService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating user '{}'", login);

        if (new EmailValidator().isValid(login, null)) {
            return userProfileRepository.findOneWithAuthoritiesByEmailIgnoreCase(login)
                .map(user -> createSpringSecurityUser(login, user))
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + login
                    + " was not found in the database"));
        }

        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);

        return userProfileRepository.findOneWithAuthoritiesByUsername(lowercaseLogin)
            .map(user -> createSpringSecurityUser(lowercaseLogin, user))
            .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin
                + " was not found in the database"));
    }

    private User createSpringSecurityUser(String lowercaseLogin, UserProfile userProfile) {
        if (!userProfile.getActivated()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }

        List<GrantedAuthority> grantedAuthorities = userProfile.getAuthorities().stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getName()))
            .collect(Collectors.toList());

        return new User(userProfile.getCredential().getUsername(),
            userProfile.getCredential().getPassword(), grantedAuthorities);
    }
}
package com.whiz.app.boot.domain.service;

import com.whiz.app.boot.domain.model.Authority;
import com.whiz.app.boot.domain.model.UserProfile;
import com.whiz.app.boot.domain.repository.AuthorityRepository;
import com.whiz.app.boot.domain.repository.UserProfileRepository;
import com.whiz.app.boot.interfaces.dto.PagingParamRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final AuthorityRepository authorityRepository;

    public UserProfileServiceImpl(
        UserProfileRepository userProfileRepository,
        AuthorityRepository authorityRepository) {
        this.userProfileRepository = userProfileRepository;
        this.authorityRepository = authorityRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserProfile> getAllUsers(PagingParamRequest pagingParamRequest) {
        Pageable pageable = PageRequest.of(pagingParamRequest.getCurrentPage(), pagingParamRequest.getPageSize());
        return userProfileRepository.findAllUserProfiles(pageable);
    }

    @Transactional
    @Override
    public UserProfile saveNewUser(UserProfile newUser) {
        UserProfile user = userProfileRepository.saveAndFlush(newUser);
        log.debug("====>>>> just saved user : {}", user);
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Authority> getAllAuthorities() {
        return authorityRepository.findAll(Sort.by("id"));
    }

    @Transactional
    @Override
    public Authority saveNewAuthority(Authority newAuthority) {
        Authority authority = authorityRepository.saveAndFlush(newAuthority);
        log.debug("====>>>> just saved authority : {}", authority);
        return authority;
    }

    @Transactional(readOnly = true)
    @Override
    public UserProfile getUserProfileByEmail(String email) {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findOneByEmailIgnoreCase(email);
        return userProfileOptional.orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public UserProfile getUserProfileByUsername(String username) {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findOneWithAuthoritiesByUsername(username);
        return userProfileOptional.orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public UserProfile getUserProfileById(Long id) {
        Assert.isTrue(id > 0, "UserId must be greater than 1.");
        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(id);
        return userProfileOptional.orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Authority> getAuthorityListByNames(List<String> names) {
        return authorityRepository.findAll()
            .stream()
            .filter(a -> names.contains(a.getName()))
            .collect(Collectors.toList());
    }
}
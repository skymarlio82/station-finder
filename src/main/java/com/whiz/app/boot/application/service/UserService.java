package com.whiz.app.boot.application.service;

import com.whiz.app.boot.domain.model.UserProfile;
import com.whiz.app.boot.domain.repository.UserProfileRepository;
import com.whiz.app.boot.infrastructure.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserProfileRepository userProfileRepository;

    public UserService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Transactional(readOnly = true)
    public UserProfile getUserProfileWithAuthorities() {
        Optional<UserProfile> userProfileOptional =  SecurityUtil.getCurrentUsername()
            .flatMap(userProfileRepository::findOneWithAuthoritiesByUsername);
        return userProfileOptional.orElse(null);
    }
}
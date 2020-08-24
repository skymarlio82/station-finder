package com.whiz.app.boot.domain.service;

import com.whiz.app.boot.domain.model.Authority;
import com.whiz.app.boot.domain.model.UserProfile;
import com.whiz.app.boot.interfaces.dto.PagingParamRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserProfileService {

    Page<UserProfile> getAllUsers(PagingParamRequest pagingParamRequest);
    UserProfile saveNewUser(UserProfile newUser);
    List<Authority> getAllAuthorities();
    Authority saveNewAuthority(Authority newAuthority);
    UserProfile getUserProfileByEmail(String email);
    UserProfile getUserProfileByUsername(String username);
    UserProfile getUserProfileById(Long id);
    List<Authority> getAuthorityListByNames(List<String> names);
}
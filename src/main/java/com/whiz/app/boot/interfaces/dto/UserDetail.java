package com.whiz.app.boot.interfaces.dto;

import com.whiz.app.boot.domain.model.Authority;
import com.whiz.app.boot.domain.model.UserProfile;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class UserDetail {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private boolean activated;
    @Singular
    private List<String> authorities;

    public static UserDetail from(UserProfile userProfile) {
        return builder()
            .id(userProfile.getId())
            .username(userProfile.getCredential().getUsername())
            .firstname(userProfile.getFullname().getFirstname())
            .lastname(userProfile.getFullname().getLastname())
            .email(userProfile.getEmail())
            .activated(userProfile.getActivated())
            .authorities(userProfile.getAuthorities()
                .stream()
                .map(Authority::getName)
                .collect(Collectors.toList())
            )
            .build();
    }
}
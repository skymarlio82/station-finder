
package com.whiz.app.boot.interfaces;

import com.whiz.app.boot.domain.model.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTest {
    private Long userId = 0L;
    private String userName;
    private Boolean enabled = false;
    private LocalDate lastPasswordResetDate;
    private String roleName;

    public static UserTest from(UserProfile userProfile) {
        return UserTest.builder()
            .userId(userProfile.getId())
            .userName(userProfile.getCredential().getUsername())
            .enabled(userProfile.getActivated())
            .lastPasswordResetDate(LocalDate.now())
            .roleName(userProfile.getAuthorities().get(0).getName())
            .build();
    }
}
package com.whiz.app.boot.interfaces.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for storing a user's credentials.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {

    @NotNull
    @Size(min = 1, max = 50)
    private String username;

    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    private Boolean rememberMe;

    @Override
    public String toString() {
        return "LoginForm#{" +
            "username=" + username +
            ",rememberMe=" + rememberMe +
            '}';
    }
}
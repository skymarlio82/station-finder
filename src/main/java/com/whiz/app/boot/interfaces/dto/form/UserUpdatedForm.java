package com.whiz.app.boot.interfaces.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatedForm {
    @NotNull
    private Long id;

    @NotNull
    @Size(min = 3, max = 30)
    private String firstname;

    @NotNull
    @Size(min = 3, max = 30)
    private String lastname;

    @NotNull
    @Size(min = 5, max = 50)
    @Email
    private String email;

    @NotNull
    private Boolean activated;

    private List<String> authorities = new ArrayList<>();

    @Override
    public String toString() {
        return "UserUpdatedForm#{" +
            ",firstname=" + firstname +
            ",lastname=" + lastname +
            ",email=" + email +
            ",authorities=" + authorities +
            "}";
    }
}
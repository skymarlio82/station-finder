package com.whiz.app.boot.domain.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Credential {
    @NotNull
    @Size(min = 6, max = 30)
    private String username;

    @JsonIgnore
    @NotNull
    @Size(min = 6, max = 100)
    private String password;

    @Override
    public String toString() {
        return "Credential#{" +
            "username=" + username +
            "}";
    }
}
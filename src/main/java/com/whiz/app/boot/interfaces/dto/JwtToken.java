package com.whiz.app.boot.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken {
    @JsonProperty("token")
    private String idToken;

    @Override
    public String toString() {
        return "JwtToken#{" +
            "idToken=" + idToken +
            "}";
    }
}

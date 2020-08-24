package com.whiz.app.boot.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonFormat(shape = Shape.OBJECT)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubAccessToken {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    private String scope;

    @Override
    public String toString() {
        return "GithubAccessToken#{" +
            "accessToken=" + accessToken +
            ",tokenType=" + tokenType +
            ",scope=" + scope +
            "}";
    }
}
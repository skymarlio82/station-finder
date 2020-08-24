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
public class GithubUserInfo {
    @JsonProperty("login")
    private String loginId;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("name")
    private String userName;
    @JsonProperty("email")
    private String userEmail;
    private String accessToken;

    @Override
    public String toString() {
        return "GithubUserInfo#{" +
            ",loginId=" + loginId +
            ",avatarUrl=" + avatarUrl +
            ",userName=" + userName +
            ",userEmail=" + userEmail +
            ",accessToken=" + accessToken +
            "}";
    }
}
package com.whiz.app.boot.infrastructure.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "github.oauth2")
public class GithubOauth2Properties {
    private String clientId;
    private String clientSecret;
    private String callbackUrl;
    private String tokenGetUrl;
    private String userInfoUrl;

    @Override
    public String toString() {
        return "GithubOauth2Property#{" +
            "clientId=" + clientId +
            ",clientSecret=" + clientSecret +
            ",callbackUrl=" + callbackUrl +
            ",tokenGetUrl=" + tokenGetUrl +
            ",userInfoUrl=" + userInfoUrl +
            "}";
    }
}
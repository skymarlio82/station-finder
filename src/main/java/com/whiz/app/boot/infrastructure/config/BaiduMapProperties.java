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
@ConfigurationProperties(prefix = "baidu")
public class BaiduMapProperties {
    private String mapUrl;
    private String mapAk;

    @Override
    public String toString() {
        return "BaiduMapProperty#{" +
            "mapUrl=" + mapUrl +
            ",mapAk=" + mapAk +
            "}";
    }
}
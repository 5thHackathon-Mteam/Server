package com.example.hackathon.global.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("oauth")
@Data
public class OauthProperties {

    private String clientId;
    private String clientSecret;
}

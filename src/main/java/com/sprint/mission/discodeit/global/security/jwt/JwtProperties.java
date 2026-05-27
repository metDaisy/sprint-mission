package com.sprint.mission.discodeit.global.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "discodeit.jwt")
public record JwtProperties(String secretKey,
                            long accessTokenExpiration,
                            long refreshTokenExpiration) {

}

package com.sprint.mission.discodeit.global.security.jwt;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenizer {

  private final JwtProperties jwtProperties;

  public JwtTokenizer(JwtProperties jwtProperties) {
    this.jwtProperties = jwtProperties;
  }


}

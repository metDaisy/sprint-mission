package com.sprint.mission.discodeit.global.security.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;

@Slf4j
public class JwtException extends AuthenticationException {

  public JwtException(String message) {
    super(message);
  }

  public JwtException(JwtErrorCode errorCode) {
    this(errorCode.getMessage());
    log.error("{} {}", errorCode.getStatus(), errorCode.getMessage());
  }
}

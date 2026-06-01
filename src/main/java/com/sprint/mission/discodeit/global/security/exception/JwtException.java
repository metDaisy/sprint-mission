package com.sprint.mission.discodeit.global.security.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtException extends AuthenticationException {

  public JwtException(String message) {
    super(message);
  }

  public JwtException(JwtErrorCode errorCode) {
    this(errorCode.getMessage());
  }
}

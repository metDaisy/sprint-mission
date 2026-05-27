package com.sprint.mission.discodeit.global.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum JwtErrorCode {

  TOKEN_CANT_BE_PARSED(HttpStatus.BAD_REQUEST, "Malformed JWT token string. Failed to parse."),
  UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST, "The provided JWT token is not supported."),
  INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid JWT token or signature mismatch."),
  EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "The provided JWT token has expired."),
  PAYLOAD_CANT_BE_PARSED(HttpStatus.UNAUTHORIZED, "Failed to extract claims from JWT payload."),
  ROLE_NOT_FOUND(HttpStatus.UNAUTHORIZED, "JWT payload does not contain required authorities claim.");

  private final HttpStatus status;
  private final String message;
}

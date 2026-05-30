package com.sprint.mission.discodeit.auth.exception;

import com.sprint.mission.discodeit.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {

  COMPROMISED_TOKEN_DETECTED(HttpStatus.UNAUTHORIZED.value(), "A001", "재사용된 토큰이 감지되었습니다. 모든 기기에서 로그아웃됩니다."),
  INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED.value(), "A002", "유효하지 않거나 만료된 리프레시 토큰입니다."),
  EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "A003", "토큰의 유효기간이 만료되었습니다."),
  MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "A004", "손상되거나 변조된 토큰입니다."),
  INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED.value(), "A005", "토큰의 서명이 일치하지 않습니다."),
  UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "A006", "지원하지 않는 토큰 형식입니다."),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "A007", "토큰을 찾을 수 없습니다."),
  ;
  private final int status;
  private final String code;
  private final String message;
}

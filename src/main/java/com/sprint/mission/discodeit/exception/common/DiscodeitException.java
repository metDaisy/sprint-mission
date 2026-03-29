package com.sprint.mission.discodeit.exception.common;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class DiscodeitException extends RuntimeException {

  private final Map<String, Object> details = new HashMap<>();
  private final Instant timestamp = Instant.now();
  private final ErrorCode errorCode;

  public DiscodeitException(ErrorCode errorCode, Map<String, Object> detail) {
    this(errorCode);
    addDetail(detail);
  }

  public DiscodeitException(ErrorCode errorCode, UUID id) {
    this(errorCode);
  }

  public void addDetail(Map<String, Object> detail) {
    details.putAll(detail);
  }

  @Override
  public String getMessage() {
    return errorCode.getMessage();
  }

  public String getCode() {
    return errorCode.getCode();
  }

  public int getStatus() {
    return errorCode.getStatus();
  }

  public String getExceptionType() {
    return getClass().getSimpleName();
  }
}

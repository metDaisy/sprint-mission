package com.sprint.mission.discodeit.exception.user;

import com.sprint.mission.discodeit.exception.common.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {
  USERID_NOT_FOUND(404, "U001", "User id not found"),
  USERNAME_OR_PASSWORD_INCORRECT(404, "U002", "Username or password incorrect"),
  USERNAME_ALREADY_EXIST(400, "U003", "Username already exists"),
  EMAIL_ALREADY_EXIST(400, "U004", "Email already exists"),
  USERSTATUS_NOT_FOUND(404, "U005", "UserStatus not found")
  ;

  private final int status;
  private final String code;
  private final String message;
}

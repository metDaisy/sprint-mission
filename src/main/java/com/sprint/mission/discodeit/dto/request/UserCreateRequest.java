package com.sprint.mission.discodeit.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sprint.mission.discodeit.common.validator.UpdateSafe;
import com.sprint.mission.discodeit.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;

/**
 * DTO for {@link User}
 */
@Getter
public final class UserCreateRequest implements Serializable {

  @Serial
  private static final long serialVersionUID = 12345L;

  @UpdateSafe
  @NotEmpty
  private final String username;

  @UpdateSafe
  @NotEmpty
  @Email
  private final String email;

  @UpdateSafe
  @NotEmpty
  private final String password;

  @JsonCreator
  public UserCreateRequest(
      @JsonProperty("username") String username,
      @JsonProperty("email") String email,
      @JsonProperty("password") String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  @Override
  public String toString() {
    return "UserCreateRequest[" +
        "username=" + username + ", " +
        "email=" + email + ", " +
        "password=" + password + ']';
  }

}

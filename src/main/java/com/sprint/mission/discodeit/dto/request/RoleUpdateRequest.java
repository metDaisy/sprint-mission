package com.sprint.mission.discodeit.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sprint.mission.discodeit.auth.constant.DiscodeitRole;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RoleUpdateRequest {

  @NotNull
  private final UUID userId;
  @NotNull
  private final DiscodeitRole role;

  public RoleUpdateRequest(
      @JsonProperty("userId") UUID userId,
      @JsonProperty("newRole") DiscodeitRole role) {
    this.userId = userId;
    this.role = role;
  }
}

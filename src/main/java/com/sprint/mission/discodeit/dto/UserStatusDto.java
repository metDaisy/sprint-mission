package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.UserStatus;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link UserStatus}
 */
public record UserStatusDto(@NotNull UserDto user, @NotNull Instant lastActiveAt)
    implements Serializable {

}

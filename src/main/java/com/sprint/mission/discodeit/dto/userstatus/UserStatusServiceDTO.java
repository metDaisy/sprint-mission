package com.sprint.mission.discodeit.dto.userstatus;

import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserResponse;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public interface UserStatusServiceDTO {
    record UserStatusResponse(UUID id, UserResponse user, LocalDateTime lastActiveAt) {
    }

    record UserStatusDto(UUID id, UUID userId, Instant lastActiveAt) {
    }

}

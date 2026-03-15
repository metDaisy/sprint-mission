package com.sprint.mission.discodeit.dto.userstatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public interface UserStatusServiceDTO {
    record UserStatusResponse(UUID id, UUID userId, LocalDateTime lastActiveAt) {
    }

    record UserStatusDto(UUID id, UUID userId, Instant lastActiveAt) {
    }

}

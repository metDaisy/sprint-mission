package com.sprint.mission.discodeit.dto.userstatus;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

public interface UserStatusServiceDTO {

    @Builder
    record UserStatusDto(UUID id, UUID userId, LocalDateTime lastActiveAt) {
    }
}

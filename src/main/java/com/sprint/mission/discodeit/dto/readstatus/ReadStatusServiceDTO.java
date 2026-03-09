package com.sprint.mission.discodeit.dto.readstatus;

import lombok.Builder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public interface ReadStatusServiceDTO {
    // todo: error log
    @Builder
    record ReadStatusResponse(UUID id, UUID userId, UUID channelId, LocalDateTime lastReadAt) {
    }

    @Builder
    record ReadStatusDto(UUID id, UUID userId, UUID channelId, Instant lastReadAt)
            implements ReadStatusCreateDto, ReadStatusUpdateDto {
    }

    interface ReadStatusCreateDto extends CreatableDto {
        Instant lastReadAt();
    }

    interface ReadStatusUpdateDto {
        UUID id();
        Instant lastReadAt();
    }

    interface CreatableDto {
        UUID userId();
        UUID channelId();
    }
}

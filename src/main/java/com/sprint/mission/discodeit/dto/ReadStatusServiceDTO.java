package com.sprint.mission.discodeit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sprint.mission.discodeit.entity.ReadType;
import lombok.Builder;
import lombok.NonNull;

import java.util.UUID;

public interface ReadStatusServiceDTO {
    record ReadStatusCreateRequest(@NonNull UUID userId, @NonNull UUID channelId) {
    }

    record ReadStatusUpdateRequest(@NonNull UUID channelId, @NonNull UUID userId,
                                   @JsonProperty("type") @NonNull ReadType type) {
    }

    @Builder
    record ReadStatusResponse(@NonNull UUID id, @NonNull UUID userId, @NonNull UUID channelId,
                              @NonNull ReadType type) {
    }
}

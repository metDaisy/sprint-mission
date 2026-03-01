package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.ChannelType;
import jakarta.annotation.Nonnull;
import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public interface ChannelServiceDTO {

    record PublicChannelCreateRequest(@Nonnull String name, @Nonnull String description) {
    }

    record PrivateChannelCreateRequest(@Nonnull List<UUID> participantIds) {
    }

    record PublicChannelUpdateRequest(@NonNull UUID channelId, String newName, String newDescription) {
    }

    // todo: error log
    @Builder
    record ChannelResponse(UUID id, String name, String description, ChannelType type,
                           List<UUID> participantIds, LocalDateTime createdAt, LocalDateTime updatedAt) {
        public ChannelResponse {
            if (type == ChannelType.PUBLIC) {
                Objects.requireNonNull(name);
                Objects.requireNonNull(description);
            }
        }
    }
}

package com.sprint.mission.discodeit.dto.channel.request;

import com.sprint.mission.discodeit.entity.ChannelType;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record PrivateChannelCreateRequest(@NotNull List<UUID> participantIds, ChannelType type) {
    public PrivateChannelCreateRequest(@NotNull List<UUID> participantIds) {
        this(participantIds, ChannelType.PRIVATE);
    }
}

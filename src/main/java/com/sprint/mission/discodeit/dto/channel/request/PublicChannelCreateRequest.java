package com.sprint.mission.discodeit.dto.channel.request;

import com.sprint.mission.discodeit.entity.ChannelType;
import jakarta.validation.constraints.NotNull;

public record PublicChannelCreateRequest(@NotNull String name, @NotNull String description, ChannelType type) {
    public PublicChannelCreateRequest(@NotNull String name, @NotNull String description) {
        this(name, description, ChannelType.PUBLIC);
    }
}

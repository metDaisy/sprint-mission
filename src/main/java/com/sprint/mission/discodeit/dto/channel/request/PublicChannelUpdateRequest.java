package com.sprint.mission.discodeit.dto.channel.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sprint.mission.discodeit.entity.ChannelType;

public record PublicChannelUpdateRequest(@JsonProperty("newName") String name,
                                         @JsonProperty("newDescription") String description,
                                         ChannelType type) {
    public PublicChannelUpdateRequest(@JsonProperty("newName") String name, @JsonProperty("newDescription") String description) {
        this(name, description, ChannelType.PUBLIC);
    }
}

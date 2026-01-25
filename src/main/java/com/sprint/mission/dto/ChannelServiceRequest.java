package com.sprint.mission.dto;

import java.util.UUID;

public interface ChannelServiceRequest extends ServiceRequest {
    record ChannelCreation(String channelName) implements ChannelServiceRequest {
        public ChannelCreation {
            validate(channelName);
        }
    }

    record ChannelNameUpdate(UUID channelId, String newChannelName) implements ChannelServiceRequest {
        public ChannelNameUpdate {
            validate(channelId, newChannelName);
        }
    }
}

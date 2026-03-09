package com.sprint.mission.discodeit.dto.channel;

import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserResponse;
import com.sprint.mission.discodeit.entity.ChannelType;
import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ChannelServiceDTO {
    // todo: error log
    @Builder
    record ChannelResponse(UUID id, ChannelType type, String name, String description,
                           List<UserResponse> participants, Instant lastMessageAt) {
    }

    @Builder
    record ChannelDto(UUID id, String name, String description, ChannelType type,
                      List<UserResponse> participants, Instant lastMessageAt)
            implements PublicChannelCreateDto, PrivateChannelCreateDto, PublicChannelUpdateDto {
    }

    interface PublicChannelCreateDto {
        String name();

        String description();

        ChannelType type();
    }

    interface PrivateChannelCreateDto {
        List<UserResponse> participants();

        ChannelType type();
    }

    interface PublicChannelUpdateDto {
        UUID id();

        String name();

        String description();
    }
}

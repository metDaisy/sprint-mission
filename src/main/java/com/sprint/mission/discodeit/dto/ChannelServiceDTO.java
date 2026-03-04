package com.sprint.mission.discodeit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserDto;
import com.sprint.mission.discodeit.entity.ChannelType;
import jakarta.annotation.Nonnull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ChannelServiceDTO {

    record PublicChannelCreateRequest(@Nonnull String name, @Nonnull String description) {
    }

    record PrivateChannelCreateRequest(@Nonnull List<UUID> participantIds) {
    }

    record PublicChannelUpdateRequest(@JsonProperty("newName") String name,
                                      @JsonProperty("newDescription") String description) {
    }

    record PublicChannelUpdateCommand(UUID id, String name, String description) {
    }

    // todo: error log
    @Builder
    record ChannelDto(UUID id, String name, String description, ChannelType type,
                      List<UserDto> participants, LocalDateTime lastMessageAt) {
    }
}

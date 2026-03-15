package com.sprint.mission.discodeit.dto.message;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentServiceDTO.BinaryContentDto;
import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentServiceDTO.BinaryContentResponse;
import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.ChannelResponse;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserResponse;
import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface MessageServiceDTO {
    // todo: error log
    @Builder
    record MessageResponse(UUID id, UserResponse author, ChannelResponse channel, String content,
                           List<BinaryContentResponse> attachments, Instant createdAt,
                           Instant updatedAt) {

    }

    @Builder
    record MessageDto(UUID id, UUID authorId, UUID channelId, String content,
                      List<BinaryContentDto> attachments)
            implements MessageCreateDto, MessageUpdateDto {
    }

    interface MessageCreateDto extends AuthorAndChannelId {
        String content();

        List<BinaryContentDto> attachments();
    }

    interface MessageUpdateDto extends AuthorAndChannelId {
        UUID id();

        String content();

        List<BinaryContentDto> attachments();
    }

    interface AuthorAndChannelId {
        UUID authorId();

        UUID channelId();
    }
}

package com.sprint.mission.discodeit.channel.controller.dto.response;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.channel.domain.entity.constant.ChannelType;
import com.sprint.mission.discodeit.user.controller.dto.response.UserResponse;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

/**
 * DTO for {@link Channel}
 */
@Builder
public record ChannelResponse(UUID id,
                              ChannelType type,
                              String name,
                              String description,
                              List<UserResponse> participants,
                              Instant lastMessageAt)
    implements Serializable {

}

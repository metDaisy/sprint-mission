package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.Channel;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.sprint.mission.discodeit.entity.ReadStatus}
 */
public record ReadStatusDto(@NotNull UserDto user, @NotNull Channel channel,
                            @NotNull Instant lastReadAt) implements
    Serializable {

}

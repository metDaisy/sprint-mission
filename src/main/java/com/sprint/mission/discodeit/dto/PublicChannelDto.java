package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO for {@link Channel}
 */
public record PublicChannelDto(@NotNull ChannelType type, @NotBlank String name,
                               @NotBlank String description)
    implements Serializable {

}

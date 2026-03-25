package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link Message}
 */
public record MessageDto(@NotBlank String content, @NotNull Channel channel,
                         @NotNull UserDto author, Set<BinaryContentDto> attachments)
    implements Serializable {

}

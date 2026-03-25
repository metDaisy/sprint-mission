package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.Channel;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link Channel}
 */
public record PrivateChannelDto(@NotNull Set<ReadStatusDto> readStatuses) implements Serializable {

}

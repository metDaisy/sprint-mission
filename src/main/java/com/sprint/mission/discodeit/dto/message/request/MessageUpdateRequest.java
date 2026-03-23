package com.sprint.mission.discodeit.dto.message.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record MessageUpdateRequest(@JsonProperty("newContent") @NotNull String content) {
}

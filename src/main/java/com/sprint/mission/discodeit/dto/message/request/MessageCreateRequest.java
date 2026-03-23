package com.sprint.mission.discodeit.dto.message.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MessageCreateRequest(@NotNull UUID authorId, @NotNull UUID channelId, @NotNull String content) {
}

package com.sprint.mission.discodeit.dto.readstatus.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReadStatusCreateRequest(@NotNull UUID userId,
                                      @NotNull UUID channelId,
                                      @NotNull @DateTimeFormat LocalDateTime lastReadAt) {
}

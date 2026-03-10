package com.sprint.mission.discodeit.dto.userstatus.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record UserStatusUpdateRequest(@NotNull @JsonProperty("newLastActiveAt") @DateTimeFormat LocalDateTime datetime) {
}

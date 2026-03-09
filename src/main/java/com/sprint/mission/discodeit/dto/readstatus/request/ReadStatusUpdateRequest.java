package com.sprint.mission.discodeit.dto.readstatus.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record ReadStatusUpdateRequest(@DateTimeFormat @NotNull LocalDateTime newLastReadAt) {
}

package com.sprint.mission.discodeit.dto.readstatus.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ReadStatusUpdateRequest(@DateTimeFormat LocalDate newLastReadAt) {
}

package com.sprint.mission.discodeit.common.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public interface TimeConverter {
    static LocalDate toDateTime(Instant time) {
        return LocalDate.ofInstant(time, ZoneId.systemDefault());
    }

    static Instant toInstant(LocalDate datetime) {
        return datetime.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }
}

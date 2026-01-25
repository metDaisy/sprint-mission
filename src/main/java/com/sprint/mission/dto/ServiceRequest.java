package com.sprint.mission.dto;

import java.util.Objects;

public interface ServiceRequest {
    default void validate(Object... objects) {
        for (Object object : objects) {
            Objects.requireNonNull(object, "have to be non-null");
        }
    }
}

package com.sprint.mission.dto;

import java.util.UUID;

public interface MessageServiceRequest extends ServiceRequest {

    record MessageUpdate(UUID messageId, String newContent) implements MessageServiceRequest {
        public MessageUpdate {
            validate(messageId, newContent);
        }
    }
}

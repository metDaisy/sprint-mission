package com.sprint.mission.dto;

import java.util.UUID;

public interface UserServiceRequest extends ServiceRequest {

    record UserCreation(String userName) implements UserServiceRequest {
        public UserCreation {
            validate(userName);
        }
    }

    record UserNameUpdate(UUID userId, String newUserName) implements UserServiceRequest {
        public UserNameUpdate {
            validate(userId, newUserName);
        }
    }
}

package com.sprint.mission.discodeit.dto.auth;

import jakarta.validation.constraints.NotNull;

public interface AuthServiceDTO {
    record LoginRequest(@NotNull String username, @NotNull String password) {
    }
}

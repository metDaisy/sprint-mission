package com.sprint.mission.discodeit.dto;

import jakarta.annotation.Nonnull;

public interface AuthServiceDTO {
    record LoginRequest(@Nonnull String username, @Nonnull String password) {
    }
}

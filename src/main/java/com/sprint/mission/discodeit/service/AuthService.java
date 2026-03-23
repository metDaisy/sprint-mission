package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.AuthServiceDTO.LoginRequest;

public interface AuthService {
    boolean login(LoginRequest request);
}

package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.AuthServiceDTO.LoginRequest;
import com.sprint.mission.discodeit.dto.UserServiceDTO.UserCreateRequest;
import com.sprint.mission.discodeit.dto.UserServiceDTO.UserResponse;
import com.sprint.mission.discodeit.dto.UserServiceDTO.UserUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface UserService extends DomainService<UserResponse, UserCreateRequest, UserUpdateRequest> {
    UserResponse find(LoginRequest dto);

    List<UserResponse> findAll();

    UserResponse updateActiveAt(UUID id);
}

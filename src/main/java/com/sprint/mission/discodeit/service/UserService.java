package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserCreateDto;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserResponse;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserUpdateDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse create(UserCreateDto dto);

    UserResponse find(UUID id);

    List<UserResponse> findAll();

    UserResponse update(UserUpdateDto dto);

    void delete(UUID id);
}

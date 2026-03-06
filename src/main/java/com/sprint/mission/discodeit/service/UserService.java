package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserCreateDto;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserResponse;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserUpdateDto;
import com.sprint.mission.discodeit.dto.user.request.UserFindRequest;

import java.util.List;

public interface UserService extends DomainService<UserResponse, UserCreateDto, UserUpdateDto> {
    UserResponse find(UserFindRequest request);

    List<UserResponse> findAll();
}

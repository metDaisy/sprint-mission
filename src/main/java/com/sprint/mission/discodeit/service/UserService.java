package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.FileUploadDto;
import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.lang.Nullable;

public interface UserService {

  UserDto create(UserCreateRequest request, @Nullable FileUploadDto profile);

  UserDto find(UUID id);

  List<UserDto> findAll();

  UserDto update(UUID id, UserUpdateRequest request, @Nullable FileUploadDto profile);

  void delete(UUID id);
}

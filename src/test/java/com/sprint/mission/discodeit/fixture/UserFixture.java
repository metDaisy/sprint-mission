package com.sprint.mission.discodeit.fixture;

import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.User;

public final class UserFixture {

  private UserFixture() {
  }

  public static UserDto createUserDto() {
    return new UserDto("leee", "leee@lee.com", "abcde",
        BinaryContentFixture.createDto(),
        UserStatusFixture.createDto());
  }

  public static UserCreateRequest createRequest() {
    UserDto dto = createUserDto();
    return new UserCreateRequest(dto.username(), dto.email(), dto.password());
  }

  public static User createEntity() {
    UserDto dto = createUserDto();
    return new User(dto.username(), dto.email(), dto.password(), null, null);
  }

  public static UserUpdateRequest createUpdate() {
    UserDto dto = createUserDto();
    return new UserUpdateRequest(dto.username(), dto.email(), dto.password());
  }
}

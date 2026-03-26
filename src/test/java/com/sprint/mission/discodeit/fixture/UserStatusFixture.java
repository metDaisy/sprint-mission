package com.sprint.mission.discodeit.fixture;

import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.dto.UserStatusDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.UserStatusMapper;
import java.time.Instant;
import org.mapstruct.factory.Mappers;

public final class UserStatusFixture {

  private UserStatusFixture() {}

  public static UserStatus createEntity() {
    return new UserStatus(User.builder().build(), Instant.now());
  }

  public static UserStatusDto createDto() {
    UserStatus entity = createEntity();
    User user = entity.getUser();
    UserDto dto = new UserDto(user.getUsername(), user.getEmail(), user.getPassword(), null, null);
    return new UserStatusDto(dto, entity.getLastActiveAt());
  }
}

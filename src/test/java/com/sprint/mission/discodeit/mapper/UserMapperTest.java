package com.sprint.mission.discodeit.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.fixture.BinaryContentFixture;
import com.sprint.mission.discodeit.fixture.UserFixture;
import com.sprint.mission.discodeit.fixture.UserStatusFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserMapperTest {

  private UserMapper userMapper;
  private User emptyUser;
  private UserDto emptyDto;

  @BeforeEach
  void setup() {
    BinaryContentMapper binaryContentMapper = new BinaryContentMapperImpl();
    userMapper = new UserMapperImpl(binaryContentMapper);

    emptyUser = User.builder().build();
    emptyDto = UserDto.builder().build();
  }

  @Test
  @DisplayName(""
      + "@Mapping(target = \"status\", qualifiedByName = \"toEntity\")\n"
      + "User toEntityFrom(UserCreateRequest request, BinaryContent profile);")
  void toEntityFrom() {
    UserCreateRequest request = UserFixture.createRequest();
    BinaryContent profile = BinaryContentFixture.createEntity();
    UserStatus status = UserStatusFixture.createOnline();
    User user = userMapper.toEntityFrom(request, status, profile);
    notEqualsTo(user, emptyUser);
  }

  @Test
  @DisplayName("UserDto toDto(User user);")
  void toDto() {
    User user = UserFixture.createEntity();
    UserDto dto = userMapper.toDto(user);
    notEqualsTo(dto, emptyDto);
  }

  @Test
  @DisplayName("User partialUpdate(UserUpdateRequest request, BinaryContent profile, @MappingTarget User user);")
  void partialUpdate() {
    UserUpdateRequest request = UserFixture.createUpdate();
    BinaryContent profile = BinaryContentFixture.createEntity();
    User user = User.builder().build();
    userMapper.partialUpdate(request, profile, user);
    notEqualsTo(user, emptyUser);
  }

  private static void notEqualsTo(User user, User emptyUser) {
    assertAll(
        () -> assertNotEquals(user.getUsername(), emptyUser.getUsername()),
        () -> assertNotEquals(user.getEmail(), emptyUser.getEmail()),
        () -> assertNotEquals(user.getPassword(), emptyUser.getPassword())
    );
  }

  private static void notEqualsTo(UserDto userDto, UserDto emptyDto) {
    assertAll(
        () -> assertNotEquals(userDto.username(), emptyDto.username()),
        () -> assertNotEquals(userDto.email(), emptyDto.email()),
        () -> assertNotEquals(userDto.password(), emptyDto.password())
    );
  }
}

package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.common.exception.code.ErrorCode;
import com.sprint.mission.discodeit.common.exception.custom.APIException;
import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.fixture.BinaryContentFixture;
import com.sprint.mission.discodeit.fixture.UserFixture;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BasicUserServiceTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  private List<UserDto> userDtos = new ArrayList<>();

  @BeforeEach
  void setUp() {
    userDtos.clear();
    for (int i = 0; i < 10; i++) {
      UserCreateRequest request = UserFixture.createRequest();
      MultipartFile profile = BinaryContentFixture.createFile();
      userDtos.add(userService.create(request, profile));
    }
  }

  @Test
  @DisplayName("success to find user by id")
  void success_to_find() {
    UserDto userDto = userDtos.get(0);
    UserDto expected = userService.find(userDto.id());
    Assertions.assertThat(expected)
        .extracting("id", "username", "email", "password")
        .containsExactly(userDto.id(), userDto.username(), userDto.email(), userDto.password());
  }

  @Test
  @DisplayName("fail to find user by id")
  void fail_to_find() {
    UUID userId = UUID.randomUUID();
    Assertions.assertThatThrownBy(() -> userService.find(userId))
        .isInstanceOf(APIException.class)
        .hasMessage(String.join(", ", ErrorCode.USERID_NOT_FOUND.getMessage(), userId.toString()));
  }

  @Test
  @DisplayName("success to find all user")
  void success_to_findAll() {
    List<UserDto> expected = userService.findAll();
    for (int i = 0; i < 10; i++) {
      Assertions.assertThat(expected.get(i))
          .usingRecursiveComparison()
          .isEqualTo(userDtos.get(i));
    }
  }

  @Test
  @DisplayName("success to create user")
  void success_to_create() {
    UserCreateRequest request = UserFixture.createRequest();
    MultipartFile profile = BinaryContentFixture.createFile();
    UserDto expected = userService.create(request, profile);
    Assertions.assertThat(expected)
        .extracting("username", "email", "password", "profile.fileName")
        .containsExactly(request.username(), request.email(), request.password(),
            profile.getName());
    Optional<User> user = userRepository.findById(expected.id());
    Assertions.assertThat(user)
        .isNotEmpty()
        .get()
        .extracting("id", "profile.id", "status.id")
        .isNotNull();
  }

  @Test
  @DisplayName("fail to create user due to existing username and email")
  void fail_to_create() {
    UserDto userDto = userDtos.get(0);
    MultipartFile profile = BinaryContentFixture.createFile();

    UserCreateRequest request = new UserCreateRequest(userDto.username(), userDto.email(),
        userDto.password());
    Assertions.assertThatThrownBy(() -> userService.create(request, profile))
        .isInstanceOf(APIException.class)
        .hasMessage(
            String.join(", ", ErrorCode.USERNAME_ALREADY_EXIST.getMessage(), request.username()));

    UserCreateRequest request2 = new UserCreateRequest("leee", userDto.email(), userDto.password());
    Assertions.assertThatThrownBy(() -> userService.create(request2, profile))
        .isInstanceOf(APIException.class)
        .hasMessage(
            String.join(", ", ErrorCode.EMAIL_ALREADY_EXIST.getMessage(), request2.email()));
  }

  @Test
  @DisplayName("success to update user")
  void success_to_update() {
    UserDto userDto = userDtos.get(0);
    UUID userId = userDto.id();
    UserUpdateRequest request = UserFixture.createUpdate();
    MultipartFile profile = BinaryContentFixture.createFile();
    UserDto updated = userService.update(userId, request, profile);
    Assertions.assertThat(updated)
        .extracting("id", "username", "email", "password",
            "profile.fileName", "profile.size", "profile.contentType")
        .containsExactly(userId, request.username(), request.email(), request.password(),
            profile.getName(), profile.getSize(), profile.getContentType());
    Assertions.assertThat(updated.profile().id()).isNotEqualTo(userDto.profile().id());
  }

  @Test
  @DisplayName("fail to update user due to incorrect id")
  void fail_to_update() {
    UUID userId = UUID.randomUUID();
    UserUpdateRequest request = UserFixture.createUpdate();
    Assertions.assertThatThrownBy(() -> userService.update(userId, request, null))
        .isInstanceOf(APIException.class)
        .hasMessage(String.join(", ", ErrorCode.USERID_NOT_FOUND.getMessage(), userId.toString()));
  }

  @Test
  @DisplayName("success to update partially, where username, password, profile image")
  void success_to_partial_update() {
    UserDto origin = userDtos.get(0);
    UUID userId = origin.id();
    UserUpdateRequest request = new UserUpdateRequest("leee", null, "leee1234");
    MultipartFile profile = BinaryContentFixture.createFile();
    UserDto expected = userService.update(userId, request, profile);

    Assertions.assertThat(expected)
        .extracting(UserDto::username, UserDto::password)
        .containsExactly(request.username(), request.password());
    Assertions.assertThat(expected.email()).isEqualTo(origin.email());

    Assertions.assertThat(expected.profile())
        .returns(profile.getName(), Assertions.from(BinaryContentDto::fileName))
        .returns(profile.getSize(), Assertions.from(BinaryContentDto::size))
        .returns(profile.getContentType(), Assertions.from(BinaryContentDto::contentType))
        .extracting(BinaryContentDto::id)
        .isNotEqualTo(origin.profile().id());
  }

  @Test
  @DisplayName("fail to update partially, since either username or email already exist")
  void fail_to_partial_update() {
    UserDto origin = userDtos.get(0);
    UUID userId = origin.id();
    UserUpdateRequest requestForUsername = new UserUpdateRequest(userDtos.get(1).username(), null,
        null);
    Assertions.assertThatThrownBy(() -> userService.update(userId, requestForUsername, null))
        .isInstanceOf(APIException.class)
        .hasMessage(String.join(", ", ErrorCode.USERNAME_ALREADY_EXIST.getMessage(),
            userDtos.get(1).username()));

    UserUpdateRequest requestForEmail = new UserUpdateRequest(null, userDtos.get(3).email(), null);
    Assertions.assertThatThrownBy(() -> userService.update(userId, requestForEmail, null))
        .isInstanceOf(APIException.class)
        .hasMessage(
            String.join(", ", ErrorCode.EMAIL_ALREADY_EXIST.getMessage(), userDtos.get(3).email()));
  }

  @Test
  @DisplayName("success to delete")
  void success_to_delete() {
    UUID userId = userDtos.get(0).id();
    userService.delete(userId);
    Assertions.assertThatThrownBy(() -> userService.find(userId))
        .isInstanceOf(APIException.class)
        .hasMessage(String.join(", ", ErrorCode.USERID_NOT_FOUND.getMessage(), userId.toString()));
  }

  @Test
  @DisplayName("fail to delete since deleted user")
  void fail_to_delete() {
    UUID userId = userDtos.get(0).id();
    userService.delete(userId);
    Assertions.assertThatThrownBy(() -> userService.delete(userId))
        .isInstanceOf(APIException.class)
        .hasMessage(String.join(", ", ErrorCode.USERID_NOT_FOUND.getMessage(), userId.toString()));
  }
}

package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.config.QueryDslConfig;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.fixture.UserFixture;
import com.sprint.mission.discodeit.fixture.UserStatusFixture;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.mapper.BinaryContentMapperImpl;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.mapper.UserMapperImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@DataJpaTest
@EnableJpaAuditing
@Import(QueryDslConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  private UserMapper userMapper;

  private List<User> users = new ArrayList<>();

  @BeforeEach
  void setUp() {
    BinaryContentMapper binaryContentMapper = new BinaryContentMapperImpl();
    userMapper = new UserMapperImpl(binaryContentMapper);
    for (int i = 0; i < 10; i++) {
      User user = getUser();
      users.add(user);
    }
    userRepository.saveAllAndFlush(users);
  }

  @Test
  @DisplayName("유저를 저장하고 조회한다")
  void save_and_find_user() {
    User actual = getUser();
    userRepository.saveAndFlush(actual);
    User expected = userRepository.findById(actual.getId()).orElseThrow();
    Assertions.assertThat(expected)
        .isNotNull()
        .usingRecursiveComparison()
        .isEqualTo(actual);
  }

  @Test
  @DisplayName("같은 이름을 가진 유저 저장 실패")
  void fail_to_save_user_with_same_username() {
    String mail = "asd@ads.com";
    String password = "asd3sd";
    UserStatus status = UserStatusFixture.createOnline();
    User userAsSameName = User.builder()
        .username(users.get(0).getUsername())
        .email(mail)
        .password(password)
        .status(status)
        .build();
    status.setUser(userAsSameName);
    Assertions.assertThatThrownBy(() -> userRepository.saveAndFlush(userAsSameName))
        .isInstanceOf(DataIntegrityViolationException.class)
        .hasMessageContaining("username");
  }

  @Test
  @DisplayName("같은 이메일을 가진 유저 저장 실패")
  void fail_to_save_user_with_same_email() {
    String username = "leeee123";
    String mail = users.get(1).getEmail();
    String password = "asd3sd";
    UserStatus status = UserStatusFixture.createOnline();
    User userAsSameName = User.builder()
        .username(username)
        .email(mail)
        .password(password)
        .status(status)
        .build();
    status.setUser(userAsSameName);
    Assertions.assertThatThrownBy(() -> userRepository.saveAndFlush(userAsSameName))
        .isInstanceOf(DataIntegrityViolationException.class)
        .hasMessageContaining("email");
  }

  @Test
  @DisplayName("이름, 비밀번호 같은 유저를 찾는다")
  void find_by_username_and_password() {
    for (User user : users) {
      User expected = userRepository.findByUsernameAndPassword(user.getUsername(),
          user.getPassword()).orElse(null);
      Assertions.assertThat(expected)
          .isNotNull()
          .usingRecursiveComparison()
          .isEqualTo(user);
    }
  }

  @Test
  @DisplayName("이름과 비밀번호로 유저를 찾는데 이름이 잘못되면 찾을 수 없다")
  void fail_to_find_by_password_and_incorrect_username() {
    for (User user : users) {
      User expected = userRepository.findByUsernameAndPassword("cjdu3dj83", user.getPassword())
          .orElse(null);
      Assertions.assertThat(expected).isNull();
    }
  }

  @Test
  @DisplayName("이름과 비밀번호로 유저를 찾는데 비밀번호가 잘못되면 찾을 수 없다")
  void fail_to_find_by_username_and_incorrect_password() {
    for (User user : users) {
      User expected = userRepository.findByUsernameAndPassword(user.getUsername(), "  ")
          .orElse(null);
      Assertions.assertThat(expected).isNull();
    }
  }

  @Test
  @DisplayName("DB 에 있는 id를 필터링 해준다")
  void filterExistingIds() {
    List<UUID> userIds = new ArrayList<>();
    IntStream.range(0, 10).forEach(i -> userIds.add(UUID.randomUUID()));
    users.forEach(user -> userIds.add(user.getId()));
    int beforeSize = userIds.size();
    List<UUID> existingUserIds = userRepository.filterExistingIds(userIds);
    int afterSize = existingUserIds.size();
    Assertions.assertThat(beforeSize)
        .isNotEqualTo(afterSize)
        .isGreaterThan(afterSize);
    for (UUID id : existingUserIds) {
      User user = userRepository.findById(id).orElse(null);
      Assertions.assertThat(user).isNotNull();
    }
  }

  private User getUser() {
    UserCreateRequest request = UserFixture.createRequest();
    UserStatus status = UserStatusFixture.createOnline();
    return userMapper.toEntityFrom(request, status, null);
  }
}

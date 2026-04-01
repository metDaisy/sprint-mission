package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.fixture.UserStatusFixture;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

class UserRepositoryTest extends BaseRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BinaryContentRepository binaryContentRepository;

  @Autowired
  private UserStatusRepository userStatusRepository;

  @BeforeEach
  void setUp() {
    initMappers();
    initUsers(userRepository);
    em.clear();
    queryInspector.clear();
  }

  @Test
  @DisplayName("유저를 저장하고 조회한다")
  void save_and_find_user() {
    User actual = getUser();
    userRepository.saveAndFlush(actual);
    ensureQueryCount(3);
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
          .withEqualsForType(this::compareInstant, Instant.class)
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
    ensureQueryCount(1);
    int afterSize = existingUserIds.size();
    Assertions.assertThat(beforeSize)
        .isNotEqualTo(afterSize)
        .isGreaterThan(afterSize);
    for (UUID id : existingUserIds) {
      User user = userRepository.findById(id).orElse(null);
      Assertions.assertThat(user).isNotNull();
    }
  }

  @ParameterizedTest(name = "유저 삭제 시 프로필 이미지, 유저 상태도 같이 삭제 된다")
  @ValueSource(ints = {0, 1, 2, 3})
  void success_to_delete_user_and_related(int index) {
    UUID userId = users.get(index).getId();
    User user = userRepository.findById(userId).orElseThrow();
    UserStatus status = user.getStatus();
    BinaryContent profile = user.getProfile();
    ensureQueryCount(1);
    userRepository.delete(user);
    flushAndClear();
    ensureQueryCount(4);
    Assertions.assertThat(userRepository.existsById(user.getId())).isFalse();
    Assertions.assertThat(binaryContentRepository.existsById(profile.getId())).isFalse();
    Assertions.assertThat(userStatusRepository.existsById(status.getId())).isFalse();
  }
}

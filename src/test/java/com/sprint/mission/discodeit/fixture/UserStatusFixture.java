package com.sprint.mission.discodeit.fixture;

import com.sprint.mission.discodeit.entity.UserStatus;
import java.time.Instant;

public final class UserStatusFixture {

  private UserStatusFixture() {}

  public static UserStatus createEntity() {
    return UserStatus.builder().lastActiveAt(Instant.now()).build();
  }
}

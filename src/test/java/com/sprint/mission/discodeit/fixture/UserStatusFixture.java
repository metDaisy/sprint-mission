package com.sprint.mission.discodeit.fixture;

import com.sprint.mission.discodeit.dto.request.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.UserStatus;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

public final class UserStatusFixture {

  private UserStatusFixture() {}

  public static UserStatus createOnline() {
    return UserStatus.builder().lastActiveAt(Instant.now()).build();
  }

  public static UserStatusUpdateRequest createUpdate() {
    return new UserStatusUpdateRequest(LocalDateTime.now());
  }

  public static UserStatus createOffline() {
    return UserStatus.builder()
        .lastActiveAt(Instant.now().minus(Duration.ofDays(7)))
        .build();
  }
}

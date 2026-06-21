package com.sprint.mission.discodeit.user.domain.event;

import com.sprint.mission.discodeit.common.event.DomainEvent;
import com.sprint.mission.discodeit.user.domain.entity.constant.UserRole;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public final class UserRoleUpdateEvent extends DomainEvent {

  private final UUID id;
  private final UserRole oldRole;
  private final UserRole newRole;
}

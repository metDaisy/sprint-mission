package com.sprint.mission.discodeit.user.domain.event;

import com.sprint.mission.discodeit.common.event.DomainEvent;
import com.sprint.mission.discodeit.user.domain.entity.constant.UserRole;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public final class UserRoleUpdateEvent extends DomainEvent {

  private final UUID id;
  private final UserRole oldRole;
  private final UserRole newRole;

  public UserRoleUpdateEvent(UUID id, UserRole oldRole, UserRole newRole) {
    this.id = id;
    this.oldRole = oldRole;
    this.newRole = newRole;
  }
}

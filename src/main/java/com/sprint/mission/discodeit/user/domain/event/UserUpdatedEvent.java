package com.sprint.mission.discodeit.user.domain.event;

import com.sprint.mission.discodeit.common.event.DomainEvent;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public final class UserUpdatedEvent extends DomainEvent {

  private final UUID id;
  private final String password;

  public UserUpdatedEvent(UUID id, String password) {
    this.id = id;
    this.password = password;
  }
}

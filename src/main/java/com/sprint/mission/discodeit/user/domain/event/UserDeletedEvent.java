package com.sprint.mission.discodeit.user.domain.event;

import com.sprint.mission.discodeit.common.event.DomainEvent;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public final class UserDeletedEvent extends DomainEvent {

  private final UUID id;

  public UserDeletedEvent(UUID id) {
    this.id = id;
  }
}

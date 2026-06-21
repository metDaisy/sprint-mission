package com.sprint.mission.discodeit.notification.domain.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sprint.mission.discodeit.common.event.DomainEvent;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class NotificationDeletedEvent extends DomainEvent {

  private final UUID id;
  @JsonIgnore
  private final UUID receiverId;
}

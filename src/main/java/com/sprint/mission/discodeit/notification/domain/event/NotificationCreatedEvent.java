package com.sprint.mission.discodeit.notification.domain.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sprint.mission.discodeit.common.event.DomainEvent;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

@Builder
@Getter
@Accessors(fluent = true)
public class NotificationCreatedEvent extends DomainEvent {

  private final UUID id;
  @JsonIgnore
  private final UUID receiverId;
  private final String title;
  private final String content;
}

package com.sprint.mission.discodeit.message.domain.event;

import com.sprint.mission.discodeit.common.event.DomainEvent;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Builder
@Accessors(fluent = true)
public class MessageUpdatedEvent extends DomainEvent {

  private final UUID id;
  private final Instant updatedAt;
  private final UUID channelId;
  private final String content;
  private final List<UUID> attachmentIds;
  private final String attachmentStatus;
}

package com.sprint.mission.discodeit.message.domain.event;

import com.sprint.mission.discodeit.common.event.DomainEvent;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public final class MessageCreatedEvent extends DomainEvent {

  private final UUID senderId;
  private final UUID channelId;
  private final String content;

  @Builder
  public MessageCreatedEvent(UUID senderId, UUID channelId, String content) {
    this.senderId = senderId;
    this.channelId = channelId;
    this.content = content;
  }
}

package com.sprint.mission.discodeit.channel.domain.event;

import com.sprint.mission.discodeit.common.event.DomainEvent;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public final class ReadStatusCreatedEvent extends DomainEvent {

  private final UUID channelId;
  private final List<UUID> participantIds;
  private final boolean notificationEnabled;

  public ReadStatusCreatedEvent(UUID channelId, List<UUID> participantIds,
      boolean notificationEnabled) {
    this.channelId = channelId;
    this.participantIds = participantIds;
    this.notificationEnabled = notificationEnabled;
  }
}

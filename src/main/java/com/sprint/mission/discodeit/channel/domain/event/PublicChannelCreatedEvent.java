package com.sprint.mission.discodeit.channel.domain.event;

import com.sprint.mission.discodeit.common.event.DomainEvent;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Builder
@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public final class PublicChannelCreatedEvent extends DomainEvent {

  private final UUID id;
  private final String name;
  private final String description;
}

package com.sprint.mission.discodeit.binarycontent.domain.event;

import com.sprint.mission.discodeit.common.event.DomainEvent;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public final class BinaryContentFailedEvent extends DomainEvent {

  private final Map<UUID, String> failures;
}

package com.sprint.mission.discodeit.user.domain.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sprint.mission.discodeit.common.event.DomainEvent;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

@Builder
@Getter
@Accessors(fluent = true)
public final class UserUpdatedEvent extends DomainEvent {

  private final UUID id;
  private final String username;
  private final String email;
  private final UUID profileId;
  @JsonIgnore
  private final String password;
}

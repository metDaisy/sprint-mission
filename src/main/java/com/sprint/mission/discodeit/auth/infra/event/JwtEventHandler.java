package com.sprint.mission.discodeit.auth.infra.event;

import com.sprint.mission.discodeit.auth.domain.event.UserRoleUpdateEvent;
import com.sprint.mission.discodeit.auth.domain.provider.JwtRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class JwtEventHandler {
  private final JwtRegistry registry;

  @TransactionalEventListener
  public void handleUserRoleUpdatedEvent(UserRoleUpdateEvent event) {
    registry.invalidateAllByUserId(event.id());
  }
}

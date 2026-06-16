package com.sprint.mission.discodeit.global.web.ws.interceptor;

import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

public interface SubscribeCommandHandler {

  boolean canSupport(String destination);

  void validate(StompHeaderAccessor accessor);
}

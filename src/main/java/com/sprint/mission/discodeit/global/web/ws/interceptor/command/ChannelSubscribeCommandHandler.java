package com.sprint.mission.discodeit.global.web.ws.interceptor.command;

import com.sprint.mission.discodeit.global.web.ws.constant.StompConstants;
import com.sprint.mission.discodeit.global.web.ws.interceptor.SubscribeCommandHandler;
import com.sprint.mission.discodeit.global.web.ws.provider.ChannelSubscribeValidator;
import com.sprint.mission.discodeit.global.web.ws.util.StompDestinationParser;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ChannelSubscribeCommandHandler implements SubscribeCommandHandler {
  private final ChannelSubscribeValidator validator;

  @Override
  public boolean canSupport(String destination) {
    return destination.startsWith(StompConstants.CHANNEL_DESTINATION);
  }

  @Override
  public void validate(StompHeaderAccessor accessor) {
    UUID userId = UUID.fromString(accessor.getUser().getName());
    UUID channelId = getChannelId(accessor);
    validator.verifyUserInChannel(channelId, userId);
  }

  private UUID getChannelId(StompHeaderAccessor accessor) {
    String destination = accessor.getDestination();
    return StompDestinationParser.extractChannelId(destination);
  }
}

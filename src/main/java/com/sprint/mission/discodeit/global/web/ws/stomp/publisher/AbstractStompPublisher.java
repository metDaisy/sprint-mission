package com.sprint.mission.discodeit.global.web.ws.stomp.publisher;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.global.web.ws.stomp.constant.WebSocketDestinations;
import com.sprint.mission.discodeit.global.web.ws.stomp.constant.StompMessageType;
import com.sprint.mission.discodeit.message.domain.entity.Message;
import com.sprint.mission.discodeit.notification.domain.entity.Notification;
import com.sprint.mission.discodeit.user.domain.entity.User;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@RequiredArgsConstructor
public abstract class AbstractStompPublisher {

  private static final Map<Class<?>, String> dest = Map.of(
      Message.class, WebSocketDestinations.SUB_MESSAGE,
      Channel.class, WebSocketDestinations.SUB_CHANNEL,
      User.class, WebSocketDestinations.SUB_USER,
      Notification.class, WebSocketDestinations.SUB_NOTIFICATION
  );
  private final SimpMessagingTemplate messagingTemplate;
  private final Class<?> clazz;

  protected void publish(Object payload, StompMessageType type) {
    String destination = dest.get(clazz);
    messagingTemplate.convertAndSend(destination, payload, Map.of("type", type));
  }

  protected void publish(UUID targetId, Object payload, StompMessageType type) {
    String destination = dest.get(clazz).replace("{id}", targetId.toString());
    messagingTemplate.convertAndSend(destination, payload,
        Map.of("type", type));
  }
}

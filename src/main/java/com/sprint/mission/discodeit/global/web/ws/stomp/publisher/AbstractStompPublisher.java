package com.sprint.mission.discodeit.global.web.ws.stomp.publisher;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.global.web.ws.stomp.constant.StompConstants;
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
      Message.class, StompConstants.SUB_MESSAGE_DESTINATION,
      Channel.class, StompConstants.SUB_CHANNEL_DESTINATION,
      User.class, StompConstants.SUB_USER_DESTINATION,
      Notification.class, StompConstants.SUB_NOTIFICATION_DESTINATION
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

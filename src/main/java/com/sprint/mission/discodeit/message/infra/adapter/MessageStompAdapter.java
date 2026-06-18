package com.sprint.mission.discodeit.message.infra.adapter;

import com.sprint.mission.discodeit.global.web.ws.stomp.constant.StompMessageType;
import com.sprint.mission.discodeit.global.web.ws.stomp.publisher.AbstractStompPublisher;
import com.sprint.mission.discodeit.message.domain.entity.Message;
import com.sprint.mission.discodeit.message.domain.provider.MessageNotifier;
import java.util.UUID;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageStompAdapter
    extends AbstractStompPublisher implements MessageNotifier {

  public MessageStompAdapter(
      SimpMessagingTemplate messagingTemplate) {
    super(messagingTemplate, Message.class);
  }

  @Override
  public void notifyCreated(UUID channelId, Object payload) {
    publish(channelId, payload, StompMessageType.CREATED);
  }

  @Override
  public void notifyUpdated(UUID channelId, Object payload) {
    publish(channelId, payload, StompMessageType.UPDATED);
  }

  @Override
  public void notifyDeleted(UUID channelId, Object payload) {
    publish(channelId, payload, StompMessageType.DELETED);
  }
}

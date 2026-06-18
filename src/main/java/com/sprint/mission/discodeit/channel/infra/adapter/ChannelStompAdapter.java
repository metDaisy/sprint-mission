package com.sprint.mission.discodeit.channel.infra.adapter;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.channel.domain.provider.ChannelNotifier;
import com.sprint.mission.discodeit.global.web.ws.stomp.constant.StompMessageType;
import com.sprint.mission.discodeit.global.web.ws.stomp.publisher.AbstractStompPublisher;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ChannelStompAdapter extends AbstractStompPublisher implements ChannelNotifier {

  public ChannelStompAdapter(
      SimpMessagingTemplate messagingTemplate) {
    super(messagingTemplate, Channel.class);
  }

  @Override
  public void notifyCreated(Object payload) {
    publish(payload, StompMessageType.CREATED);
  }

  @Override
  public void notifyUpdated(Object payload) {
    publish(payload, StompMessageType.UPDATED);
  }

  @Override
  public void notifyDeleted(Object payload) {
    publish(payload, StompMessageType.DELETED);
  }
}

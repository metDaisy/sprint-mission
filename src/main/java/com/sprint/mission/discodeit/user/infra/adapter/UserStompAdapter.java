package com.sprint.mission.discodeit.user.infra.adapter;

import com.sprint.mission.discodeit.global.web.ws.stomp.constant.StompMessageType;
import com.sprint.mission.discodeit.global.web.ws.stomp.publisher.AbstractStompPublisher;
import com.sprint.mission.discodeit.user.domain.entity.User;
import com.sprint.mission.discodeit.user.domain.provider.UserNotifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserStompAdapter extends AbstractStompPublisher implements UserNotifier {

  public UserStompAdapter(SimpMessagingTemplate messagingTemplate) {
    super(messagingTemplate, User.class);
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

package com.sprint.mission.discodeit.auth.permission.evaluator;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

public class MessagePermissionEvaluator extends
    AbstractPermissionEvaluator<Message> {

  public MessagePermissionEvaluator(MessageRepository repository) {
    super("Message", repository);
  }
}

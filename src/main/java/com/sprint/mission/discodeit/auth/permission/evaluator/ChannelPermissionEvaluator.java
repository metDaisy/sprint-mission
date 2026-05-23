package com.sprint.mission.discodeit.auth.permission.evaluator;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import org.springframework.stereotype.Component;

@Component
public class ChannelPermissionEvaluator extends AbstractPermissionEvaluator<Channel> {

  public ChannelPermissionEvaluator(ChannelRepository repository) {
    super("Channel", repository);
  }
}

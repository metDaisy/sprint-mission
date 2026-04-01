package com.sprint.mission.discodeit.common.utils;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProxyResolver {
  private final UserRepository userRepository;
  private final ChannelRepository channelRepository;

  public User resolveUser(UUID id) {
    return userRepository.getReferenceById(id);
  }

  public Channel resolveChannel(UUID id) {
    return channelRepository.getReferenceById(id);
  }
}

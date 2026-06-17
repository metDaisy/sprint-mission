package com.sprint.mission.discodeit.message.domain.provider;

import java.util.UUID;

public interface MessageNotifier {

  void notifyCreated(UUID channelId, Object payload);

  void notifyUpdated(UUID channelId, Object payload);

  void notifyDeleted(UUID id);
}

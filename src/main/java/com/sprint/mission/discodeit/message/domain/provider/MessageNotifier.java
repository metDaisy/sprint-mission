package com.sprint.mission.discodeit.message.domain.provider;

import java.util.UUID;

public interface MessageNotifier {

  void notifyCreated(UUID targetId, Object payload);

  void notifyUpdated(UUID targetId, Object payload);

  void notifyDeleted(UUID targetId, Object payload);
}

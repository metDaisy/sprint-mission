package com.sprint.mission.discodeit.user.domain.provider;

public interface UserNotifier {

  void notifyCreated(Object payload);

  void notifyUpdated(Object payload);

  void notifyDeleted(Object payload);
}

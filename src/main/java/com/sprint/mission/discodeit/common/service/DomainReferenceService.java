package com.sprint.mission.discodeit.common.service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface DomainReferenceService<T> {

  void existsOrThrow(UUID id);

  void existsOrThrow(Collection<UUID> ids);

  T getProxy(UUID id);

  List<T> getProxy(Collection<UUID> ids);
}

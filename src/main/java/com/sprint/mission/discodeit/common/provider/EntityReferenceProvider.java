package com.sprint.mission.discodeit.common.provider;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface EntityReferenceProvider<T> {

  T getProxyOrThrow(UUID id);

  List<T> getProxyOrThrow(Collection<UUID> ids);
}

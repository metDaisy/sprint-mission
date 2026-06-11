package com.sprint.mission.discodeit.common.provider;

import com.sprint.mission.discodeit.common.service.DomainReferenceService;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractEntityReferenceProvider<T> implements EntityReferenceProvider<T> {

  protected final DomainReferenceService<T> service;

  @Override
  public List<T> getProxyOrThrow(Collection<UUID> ids) {
    service.existsOrThrow(ids);
    return service.getProxy(ids);
  }

  @Override
  public T getProxyOrThrow(UUID id) {
    service.existsOrThrow(id);
    return service.getProxy(id);
  }
}

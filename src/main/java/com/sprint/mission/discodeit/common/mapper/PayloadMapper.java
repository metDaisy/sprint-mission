package com.sprint.mission.discodeit.common.mapper;

import com.sprint.mission.discodeit.common.payload.marker.PayloadCreatedMarker;
import com.sprint.mission.discodeit.common.payload.marker.PayloadDeletedMarker;
import com.sprint.mission.discodeit.common.payload.marker.PayloadMarker;
import com.sprint.mission.discodeit.common.payload.marker.PayloadUpdatedMarker;
import com.sprint.mission.discodeit.common.jpa.BaseEntity;
import java.util.Map;
import java.util.function.Function;

public abstract class PayloadMapper<E extends BaseEntity> {

  private final Map<Class<?>, Function<E, ?>> mapContainer = Map.of(
      PayloadCreatedMarker.class, this::toCreated,
      PayloadUpdatedMarker.class, this::toUpdated,
      PayloadDeletedMarker.class, this::toDeleted
  );

  public <T extends PayloadMarker> T toDto(E entity, Class<T> clazz) {
    Function<E, ?> mapper = mapContainer.get(clazz);
    return clazz.cast(mapper.apply(entity));
  }

  protected abstract PayloadCreatedMarker toCreated(E entity);

  protected abstract PayloadUpdatedMarker toUpdated(E entity);

  protected abstract PayloadDeletedMarker toDeleted(E entity);
}

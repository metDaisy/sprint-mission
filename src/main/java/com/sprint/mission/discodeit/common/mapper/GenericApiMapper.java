package com.sprint.mission.discodeit.common.mapper;

import java.util.Collection;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.NullValueMappingStrategy;

public interface GenericApiMapper<T, R> {

  R toDto(T entity);

  @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
  List<R> toDto(Collection<T> entities);
}

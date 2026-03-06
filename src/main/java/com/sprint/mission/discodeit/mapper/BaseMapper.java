package com.sprint.mission.discodeit.mapper;

import org.mapstruct.MappingTarget;

public interface BaseMapper<D, E> {
    D toDto(E entity);

    E toEntity(D dto);

    void updateFromDto(@MappingTarget E entity, D dto);
}

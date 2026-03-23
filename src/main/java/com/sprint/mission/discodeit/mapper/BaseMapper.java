package com.sprint.mission.discodeit.mapper;

public interface BaseMapper<D, E, R> {
    D toDto(E entity);

    E toEntity(D dto);

    R toResponse(E entity);
}

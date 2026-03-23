package com.sprint.mission.repository;

import com.sprint.mission.entity.Entity;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public interface BaseRepository<T extends Entity<T>> {
    T read(UUID id);

    <U extends Collection<UUID>> Map<UUID, T> readMany(U ids);

    void write(T entity);

    void writeMany(Map<UUID, T> entities);

    void delete(UUID id);

    boolean hasId(UUID id);
}

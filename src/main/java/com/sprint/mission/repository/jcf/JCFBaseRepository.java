package com.sprint.mission.repository.jcf;

import com.sprint.mission.entity.Entity;
import com.sprint.mission.repository.BaseRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class JCFBaseRepository<T extends Entity<T>> implements BaseRepository<T> {
    private final Map<UUID, T> data;

    public JCFBaseRepository() {
        this.data = new HashMap<>();
    }

    public JCFBaseRepository(Map<UUID, T> data) {
        this.data = data;
    }

    @Override
    public T read(UUID id) {
        return data.get(id);
    }

    @Override
    public <U extends Collection<UUID>> Map<UUID, T> readMany(U ids) {
        return ids.stream()
                .map(this::read)
                .collect(Collectors.toMap(Entity::getId, entity -> entity));
    }

    @Override
    public void write(T entity) {
        data.put(entity.getId(), entity);
    }

    @Override
    public void writeMany(Map<UUID, T> entities) {
        data.putAll(entities);
    }

    @Override
    public void delete(UUID id) {
        data.remove(id);
    }

    @Override
    public boolean hasId(UUID id) {
        return data.containsKey(id);
    }
}

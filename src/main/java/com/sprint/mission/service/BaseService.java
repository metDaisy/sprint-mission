package com.sprint.mission.service;

import com.sprint.mission.entity.Entity;

import java.util.UUID;
import java.util.function.Consumer;

public abstract class BaseService<T extends Entity<T>> {
    protected final String ID_NOT_FOUND = "id not found, %s";

    protected abstract boolean hasId(UUID id);

    protected void doAction(UUID id, Consumer<T> action) {
        T entity = get(id);
        action.accept(entity);
    }

    public abstract T get(UUID id);

    //    <U extends Collection<UUID>> Map<UUID, T> getAll(U ids);
    public abstract void delete(UUID id);
}

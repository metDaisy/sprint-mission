package com.sprint.mission.service;

import com.sprint.mission.entity.Entity;

import java.util.UUID;
import java.util.function.Consumer;

public abstract class BaseTemplateService<T extends Entity<T>> implements BaseService<T> {
    protected final String ID_NOT_FOUND = "id not found, %s";

    protected abstract boolean hasId(UUID id);

    protected void doAction(UUID id, Consumer<T> action) {
        T entity = get(id);
        action.accept(entity);
    }
}

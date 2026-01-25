package com.sprint.mission.service.jcf;

import com.sprint.mission.entity.Entity;
import com.sprint.mission.service.BaseService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public abstract class JCFBaseService<T extends Entity<T>> extends BaseService<T> {
    protected final Map<UUID, T> data;

    protected JCFBaseService() {
        this.data = new HashMap<>();
    }

    protected JCFBaseService(Map<UUID, T> data) {
        this.data = data;
    }

    @Override
    protected boolean hasId(UUID id) {
        return data.containsKey(Objects.requireNonNull(id));
    }

    @Override
    public T get(UUID id) {
        if (!hasId(id)) {
            throw new IllegalArgumentException(ID_NOT_FOUND.formatted(id));
        }
        return data.get(id);
    }

//    @Override
//    public <U extends Collection<UUID>> Map<UUID, T> getAll(U ids) {
//        U notNullIds = Objects.requireNonNull(ids);
//        return notNullIds.stream()
//                .map(this::get)
//                .collect(Collectors.toMap(T::getId, entity -> entity));
//    }

    @Override
    public void delete(UUID id) {
        if (!hasId(id)) {
            throw new IllegalArgumentException(String.format(ID_NOT_FOUND.formatted(id)));
        }
        data.remove(id);
    }

}

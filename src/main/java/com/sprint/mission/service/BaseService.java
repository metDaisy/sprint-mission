package com.sprint.mission.service;

import com.sprint.mission.entity.Entity;

import java.util.UUID;

public interface BaseService<T extends Entity<T>> {
    T get(UUID id);
    void delete(UUID id);
}

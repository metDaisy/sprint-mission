package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.common.exception.custom.APIException;
import com.sprint.mission.discodeit.repository.DomainRepository;

import java.util.UUID;
import java.util.function.Supplier;

public abstract class BasicDomainService<T> {
    protected final String ID_NOT_FOUND = "%s with id %s not found";

    protected <R> R findEntityById(UUID id, DomainRepository<R> repository, Supplier<? extends APIException> exception) {
        return repository.findById(id).orElseThrow(exception);
    }

    protected void deleteIfExist(UUID id, DomainRepository<?> repository, Supplier<? extends APIException> exception) {
        if (id == null) {
            return;
        }
        if (!repository.existsById(id)) {
            throw exception.get();
        }
        repository.deleteById(id);
    }

    protected abstract T findById(UUID id);
}

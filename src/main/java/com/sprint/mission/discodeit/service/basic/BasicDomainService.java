package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.common.exception.custom.APIException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BasicDomainService<T> {

    protected <R, U> U getOrThrow(R value, Function<R, Optional<U>> action, Supplier<? extends APIException> exception) {
        return action.apply(value).orElseThrow(exception);
    }

    protected void deleteByIdOrThrow(UUID id, JpaRepository<T, UUID> repository, APIException exception) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw exception;
    }

    protected <R> void ensure(R value, Function<R, Boolean> condition, Function<R, APIException> exception) {
        if (!condition.apply(value)) {
            return;
        }
        throw exception.apply(value);
    }

    protected abstract T findById(UUID id);
}

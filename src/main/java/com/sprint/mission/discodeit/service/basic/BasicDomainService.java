package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.exception.common.CommonErrorCode;
import com.sprint.mission.discodeit.exception.common.CommonException;
import com.sprint.mission.discodeit.exception.common.DiscodeitException;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.jpa.repository.JpaRepository;

// todo: add log
public abstract class BasicDomainService<T> {

  protected <R, U> U getOrThrow(R value, Function<R, Optional<U>> action,
      DiscodeitException exception) {
    return action.apply(value).orElseThrow(() -> exception);
  }

  protected void deleteByIdOrThrow(UUID id, JpaRepository<T, UUID> repository,
      DiscodeitException exception) {
    T entity = repository.findById(id).orElseThrow(() -> exception);
    try {
      repository.delete(entity);
    } catch (OptimisticLockingFailureException e) {
      // todo: new UserException(CommonErrorCode...), ChannelException(CommonErrorCode...), ...
      throw new CommonException(CommonErrorCode.OPTIMISTIC_LOCKING_FAILURE);
    }
  }

  protected <R> void ensure(R value, Function<R, Boolean> condition, DiscodeitException exception) {
    if (condition.apply(value)) {
      throw exception;
    }
  }

  protected abstract T findById(UUID id);
}

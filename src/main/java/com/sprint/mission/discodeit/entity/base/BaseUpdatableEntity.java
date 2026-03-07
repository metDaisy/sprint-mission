package com.sprint.mission.discodeit.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.function.Consumer;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseUpdatableEntity<U> extends BaseEntity {
    @Column
    @LastModifiedDate
    private Instant updatedAt;

    protected <T> void updateIfChanged(T before, T after, Consumer<T> action) {
        if (after == null || before.equals(after)) {
            return;
        }
        action.accept(after);
    }

    public abstract void update(U updateDto);
}

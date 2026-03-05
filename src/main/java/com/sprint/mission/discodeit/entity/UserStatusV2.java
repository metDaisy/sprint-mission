package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Entity
@Table(name = "user_statuses")
public class UserStatusV2 extends BaseUpdatableEntity {
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserV2 user;

    @Column
    private Instant lastActiveAt;
}
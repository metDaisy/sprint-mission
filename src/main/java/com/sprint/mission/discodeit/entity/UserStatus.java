package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Duration;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_statuses")
@EntityListeners(AuditingEntityListener.class)
public class UserStatus extends BaseUpdatableEntity {

  private static final Long ACTIVE_THRESHOLD = 300L;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  @CreatedDate
  @Column(nullable = false)
  private Instant lastActiveAt;

  @Builder
  public UserStatus(User user, Instant lastActiveAt) {
    this.user = user;
    this.lastActiveAt = lastActiveAt;
  }

  public boolean isOnline() {
    return Duration.between(lastActiveAt, Instant.now()).getSeconds() < ACTIVE_THRESHOLD;
  }

}

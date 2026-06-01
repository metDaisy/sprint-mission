package com.sprint.mission.discodeit.auth.domain.entity;

import com.sprint.mission.discodeit.common.entity.BaseUpdatableEntity;
import com.sprint.mission.discodeit.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "jwt_refresh_tokens")
public class RefreshToken extends BaseUpdatableEntity {

  @Column(name = "device", nullable = false)
  private String device;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "expires_at", nullable = false)
  private Instant expiresAt;

  @Column(name = "token", nullable = false, length = 512)
  private String token;

  @Column(name = "previous_token", length = 512)
  private String previousToken;

  @Builder
  public RefreshToken(String device, User user, String token, Instant expiresAt) {
    this.device = device;
    this.user = user;
    this.token = token;
    this.expiresAt = expiresAt;
  }

  public void rotate(String newToken, Instant expiresAt) {
    this.previousToken = this.token;
    this.token = newToken;
    this.expiresAt = expiresAt;
  }

  public boolean isExpired(Instant current) {
    return expiresAt.isBefore(current);
  }
}

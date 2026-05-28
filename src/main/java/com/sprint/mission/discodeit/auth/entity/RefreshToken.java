package com.sprint.mission.discodeit.auth.entity;

import com.sprint.mission.discodeit.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "jwt_refresh_tokens")
@EntityListeners(AuditingEntityListener.class)
public class RefreshToken {

  @Id
  @Column(name = "user_id")
  private UUID id;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(updatable = false, nullable = false)
  @CreatedDate
  private Instant createdAt;

  @Column
  @LastModifiedDate
  private Instant updatedAt;

  @Column(name = "expires_at", nullable = false)
  private Instant expiresAt;

  @Column(name = "token", nullable = false, length = 512)
  private String token;

  @Column(name = "previous_token", length = 512)
  private String previousToken;

  public RefreshToken(User user, String token, Instant expiresAt) {
    this.user = user;
    this.token = token;
    this.expiresAt = expiresAt;
  }

  public void rotate(String newToken, Instant expiresAt) {
    this.previousToken = this.token;
    this.token = newToken;
    this.expiresAt = expiresAt;
  }
}

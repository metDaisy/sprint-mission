package com.sprint.mission.discodeit.auth.infra;

import com.sprint.mission.discodeit.auth.domain.entity.RefreshToken;
import com.sprint.mission.discodeit.auth.domain.exception.AuthErrorCode;
import com.sprint.mission.discodeit.auth.domain.exception.AuthException;
import com.sprint.mission.discodeit.auth.repository.JwtRegistry;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "discodeit.jwt.registry", value = "store-type", havingValue = "in-memory")
public class InMemoryJwtRegistry implements JwtRegistry {

  @Value("${discodeit.jwt.registry.session.max-retained}")
  private int SESSION_MAX_RETAINED;
  @Value("${discodeit.jwt.registry.session.max-concurrent}")
  private int SESSION_MAX_CONCURRENT;

  private final Map<UUID, ConcurrentLinkedQueue<RefreshToken>> retainedDevice = new ConcurrentHashMap<>();
  private final Map<UUID, ConcurrentLinkedQueue<UUID>> activeSessions = new ConcurrentHashMap<>();

  @Override
  public void register(RefreshToken refreshToken) {
    registerDevice(refreshToken);
    activateSession(refreshToken);
  }

  private void activateSession(RefreshToken refreshToken) {
    Queue<UUID> queue = activeSessions.computeIfAbsent(refreshToken.getUser().getId(),
        k -> new ConcurrentLinkedQueue<>());
    queue.offer(refreshToken.getId());
    while (queue.size() > SESSION_MAX_CONCURRENT) {
      queue.poll();
    }
  }

  private void registerDevice(RefreshToken refreshToken) {
    Queue<RefreshToken> queue = retainedDevice.computeIfAbsent(refreshToken.getUser().getId(),
        k -> new ConcurrentLinkedQueue<>());
    queue.offer(refreshToken);
    while (queue.size() > SESSION_MAX_RETAINED) {
      queue.poll();
    }
  }

  @Override
  public RefreshToken findByToken(String refreshToken) {
    return retainedDevice.values().stream().flatMap(Collection::stream)
        .filter(token -> token.getToken().equals(refreshToken))
        .findFirst()
        .orElseThrow(() -> new AuthException(AuthErrorCode.TOKEN_NOT_FOUND));
  }

  @Override
  public void invalidateAllByUserId(UUID userId) {
    retainedDevice.remove(userId);
  }

  @Override
  public void invalidateByToken(String refreshToken) {
    for (Queue<RefreshToken> queue : retainedDevice.values()) {
      for (RefreshToken token : queue) {
        if (token.getToken().equals(refreshToken)) {
          queue.remove(token);
          return;
        }
      }
    }
  }

  @Override
  public void clearExpiredTokens() {
    Instant now = Instant.now();
    retainedDevice.values().forEach(queue ->
        queue.removeIf(token -> token.isExpired(now))
    );
  }
}

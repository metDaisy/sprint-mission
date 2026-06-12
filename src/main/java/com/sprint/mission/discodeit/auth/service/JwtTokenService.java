package com.sprint.mission.discodeit.auth.service;

import com.sprint.mission.discodeit.auth.presentation.dto.JwtLoginResponse;
import com.sprint.mission.discodeit.auth.presentation.mapper.AuthMapper;
import com.sprint.mission.discodeit.auth.domain.entity.RefreshToken;
import com.sprint.mission.discodeit.auth.domain.entity.UserCredential;
import com.sprint.mission.discodeit.auth.domain.exception.AuthException;
import com.sprint.mission.discodeit.auth.domain.exception.JWTErrorCode;
import com.sprint.mission.discodeit.auth.domain.exception.UserCredentialErrorCode;
import com.sprint.mission.discodeit.auth.infra.repository.UserCredentialRepository;
import com.sprint.mission.discodeit.common.support.DomainServiceSupport;
import com.sprint.mission.discodeit.global.security.jwt.JwtProperties;
import com.sprint.mission.discodeit.global.security.jwt.JwtTokenProvider;
import com.sprint.mission.discodeit.global.security.jwt.registry.JwtRegistry;
import com.sprint.mission.discodeit.user.domain.entity.constant.UserRole;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class JwtTokenService {

  private final JwtProperties jwtProperties;
  private final JwtTokenProvider jwtTokenProvider;
  private final JwtRegistry jwtRegistry;
  private final UserCredentialRepository repository;
  private final AuthMapper mapper;

  public JwtLoginResponse reissue(String refreshToken) {
    jwtTokenProvider.validate(refreshToken);

    RefreshToken token = jwtRegistry.findByToken(refreshToken);
    if (Objects.equals(token.getPreviousToken(), refreshToken)) {
      jwtRegistry.invalidateAllByUserId(token.getUser().getId());
      throw new AuthException(JWTErrorCode.INVALID_REFRESH_TOKEN);
    }
    if (!token.getToken().equals(refreshToken)) {
      throw new AuthException(JWTErrorCode.EXPIRED_TOKEN);
    }
    return issueTokensAndSave(token);
  }

  public JwtLoginResponse createJwtLogin(String username, String device) {
    UserCredential userCredential = DomainServiceSupport.getOrThrow(username,
        repository::findByUser_Email,
        value -> new AuthException(UserCredentialErrorCode.USER_CREDENTIAL_NOT_FOUND, "username",
            value));
    RefreshToken refreshEntity = RefreshToken.builder()
        .user(userCredential.getUser())
        .token("")
        .device(device)
        .expiresAt(null)
        .build();
    return issueTokensAndSave(refreshEntity);
  }

  public void deleteRefreshToken(String token) {
    jwtRegistry.invalidateByToken(token);
  }

  private JwtLoginResponse issueTokensAndSave(RefreshToken token) {
    UUID userId = token.getUser().getId();
    UserRole role = token.getUser().getRole();
    String newAccessToken = jwtTokenProvider.generateAccessToken(userId, role);
    String newRefreshToken = jwtTokenProvider.generateRefreshToken(userId);
    long expirationSeconds = jwtProperties.refreshToken().expiration();
    Instant expiresAt = Instant.now().plusSeconds(expirationSeconds);
    token.rotate(newRefreshToken, expiresAt);
    jwtRegistry.register(token);
    return mapper.toDtoFrom(token, newAccessToken, newRefreshToken);
  }
}

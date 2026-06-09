package com.sprint.mission.discodeit.auth.service;

import com.sprint.mission.discodeit.auth.controller.dto.JwtLoginResponse;
import com.sprint.mission.discodeit.auth.controller.mapper.AuthMapper;
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
import com.sprint.mission.discodeit.user.domain.entity.User;
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
  private final AuthMapper authMapper;

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
    return issueTokensAndSave(token.getUser(), token);
  }

  public JwtLoginResponse createJwtLogin(UUID userId, String device) {
    UserCredential userCredential = DomainServiceSupport.getOrThrow(userId,
        repository::findByUser_Id,
        value -> new AuthException(UserCredentialErrorCode.USER_CREDENTIAL_NOT_FOUND));
    RefreshToken refreshEntity = new RefreshToken(userCredential.getUser(), device, "",
        Instant.now());
    return issueTokensAndSave(userCredential.getUser(), refreshEntity);
  }

  public void deleteRefreshToken(String token) {
    jwtRegistry.invalidateByToken(token);
  }

  private JwtLoginResponse issueTokensAndSave(User user, RefreshToken refreshEntity) {
    String newAccessToken = jwtTokenProvider.generateAccessToken(
        user.getId().toString(), user.getRole().name());
    String newRefreshToken = jwtTokenProvider.generateRefreshToken(user.getId().toString());

    long expirationSeconds = jwtProperties.refreshToken().expiration();
    Instant expiresAt = Instant.now().plusSeconds(expirationSeconds);

    refreshEntity.rotate(newRefreshToken, expiresAt);
    jwtRegistry.register(refreshEntity);

    return new JwtLoginResponse(authMapper.toUserResponse(user), newAccessToken, newRefreshToken);
  }
}

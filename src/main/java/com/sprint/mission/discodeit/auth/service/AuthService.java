package com.sprint.mission.discodeit.auth.service;

import com.sprint.mission.discodeit.auth.dto.JwtLoginResponse;
import com.sprint.mission.discodeit.auth.entity.RefreshToken;
import com.sprint.mission.discodeit.auth.exception.AuthErrorCode;
import com.sprint.mission.discodeit.auth.exception.AuthException;
import com.sprint.mission.discodeit.auth.repository.RefreshTokenRepository;
import com.sprint.mission.discodeit.common.support.DomainServiceSupport;
import com.sprint.mission.discodeit.global.security.jwt.JwtProperties;
import com.sprint.mission.discodeit.global.security.jwt.JwtTokenProvider;
import com.sprint.mission.discodeit.user.dto.request.RoleUpdateRequest;
import com.sprint.mission.discodeit.user.dto.response.UserResponse;
import com.sprint.mission.discodeit.user.entity.User;
import com.sprint.mission.discodeit.user.exception.UserErrorCode;
import com.sprint.mission.discodeit.user.exception.UserException;
import com.sprint.mission.discodeit.user.mapper.UserMapper;
import com.sprint.mission.discodeit.user.repository.UserRepository;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

  private final JwtProperties jwtProperties;
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final JwtTokenProvider jwtTokenProvider;
  private final RefreshTokenRepository refreshTokenRepository;

  public UserResponse updateRole(RoleUpdateRequest request) {
    User user = findById(request.getUserId());
    userMapper.partialUpdate(request, user);
    refreshTokenRepository.deleteByUser(user);
    return userMapper.toDto(user);
  }

  public JwtLoginResponse refreshToken(UUID userId, String refreshToken) {
    jwtTokenProvider.validate(refreshToken);

    RefreshToken refreshEntity = findToken(userId);

    if (Objects.equals(refreshEntity.getPreviousToken(), refreshToken)) {
      refreshTokenRepository.delete(refreshEntity);
      throw new AuthException(AuthErrorCode.INVALID_REFRESH_TOKEN);
    }
    if (!refreshEntity.getToken().equals(refreshToken)) {
      throw new AuthException(AuthErrorCode.EXPIRED_TOKEN);
    }
    return issueTokensAndSave(refreshEntity.getUser(), refreshEntity);
  }

  public JwtLoginResponse createJwtLogin(UserResponse response) {
    DomainServiceSupport.requireOrThrow(response.id(), userRepository::existsById,
        value -> new UserException(UserErrorCode.USERID_NOT_FOUND, value));
    User user = userRepository.getReferenceById(response.id());

    RefreshToken refreshEntity = refreshTokenRepository.findByUser_Id(user.getId())
        .orElseGet(() -> createRefreshToken(user));

    return issueTokensAndSave(user, refreshEntity);
  }

  public void deleteRefreshToken(UUID userId) {
    // DomainServiceSupport;
  }

  private RefreshToken createRefreshToken(User user) {
    RefreshToken newToken = new RefreshToken(user, "", Instant.now());
    return refreshTokenRepository.save(newToken);
  }

  private JwtLoginResponse issueTokensAndSave(User user, RefreshToken refreshEntity) {
    String newAccessToken = jwtTokenProvider.generateAccessToken(
        user.getId().toString(), user.getRole().name());
    String newRefreshToken = jwtTokenProvider.generateRefreshToken(user.getId().toString());

    long expirationSeconds = jwtProperties.refreshTokenExpiration();
    Instant expiresAt = Instant.now().plusSeconds(expirationSeconds);

    refreshEntity.rotate(newRefreshToken, expiresAt);

    return new JwtLoginResponse(userMapper.toDto(user), newAccessToken, newRefreshToken);
  }

  private RefreshToken findToken(UUID userId) {
    return DomainServiceSupport.getOrThrow(userId, refreshTokenRepository::findByUser_Id,
        value -> new AuthException(AuthErrorCode.USER_NOT_FOUND));
  }

  private User findById(UUID id) {
    return DomainServiceSupport.getOrThrow(id, userRepository::findById,
        value -> new UserException(UserErrorCode.USERID_NOT_FOUND, value));
  }
}

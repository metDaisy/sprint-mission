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
    return userMapper.toDto(user);
  }

  public JwtLoginResponse refreshToken(UUID userId, String refreshToken) {
    jwtTokenProvider.validate(refreshToken);

    RefreshToken refresh = findToken(userId);
    if (Objects.equals(refresh.getPreviousToken(), refreshToken)) {
      refreshTokenRepository.delete(refresh);
      throw new AuthException(AuthErrorCode.INVALID_REFRESH_TOKEN);
    }
    if (!refresh.getToken().equals(refreshToken)) {
      throw new AuthException(AuthErrorCode.EXPIRED_TOKEN);
    }

    String newAccessToken = jwtTokenProvider.generateAccessToken(
        userId.toString(), refresh.getUser().getRole().name());
    String newRefreshToken = jwtTokenProvider.generateRefreshToken(userId.toString());
    long expiration = jwtProperties.refreshTokenExpiration();
    refresh.rotate(newRefreshToken, Instant.now().plusSeconds(expiration));
    return new JwtLoginResponse(userMapper.toDto(refresh.getUser()), newAccessToken,
        newRefreshToken);
  }

  public JwtLoginResponse create(UUID userId) {

    return null;
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

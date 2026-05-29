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

    RefreshToken refreshEntity = findToken(userId);

    if (Objects.equals(refreshEntity.getPreviousToken(), refreshToken)) {
      refreshTokenRepository.delete(refreshEntity);
      throw new AuthException(AuthErrorCode.INVALID_REFRESH_TOKEN);
    }
    if (!refreshEntity.getToken().equals(refreshToken)) {
      throw new AuthException(AuthErrorCode.EXPIRED_TOKEN);
    }

    // 핵심 발급 로직을 하단 메서드로 위임 (더티 체킹으로 자동 UPDATE 됨)
    return issueTokensAndSave(refreshEntity.getUser(), refreshEntity);
  }

  // ✨ 2. 최초 로그인 (Upsert 버그 수정!)
  public JwtLoginResponse createJwtLogin(UserResponse response) {
    User user = userRepository.getReferenceById(response.id());

    // DB에 해당 유저의 토큰 장부가 이미 있는지 확인합니다.
    RefreshToken refreshEntity = refreshTokenRepository.findByUser_Id(user.getId())
        .orElseGet(() -> {
          // 장부가 아예 없으면 빈 껍데기 엔티티를 새로 만들어서 저장합니다. (INSERT)
          RefreshToken newToken = new RefreshToken(user, "", Instant.now());
          return refreshTokenRepository.save(newToken);
        });

    // 핵심 발급 로직을 하단 메서드로 위임 (기존 장부면 밀어내기 UPDATE 됨)
    return issueTokensAndSave(user, refreshEntity);
  }

  // ✨ 3. 공통 토큰 발급 및 DB 덮어쓰기 로직 (DRY 원칙 적용)
  private JwtLoginResponse issueTokensAndSave(User user, RefreshToken refreshEntity) {
    String newAccessToken = jwtTokenProvider.generateAccessToken(
        user.getId().toString(), user.getRole().name());
    String newRefreshToken = jwtTokenProvider.generateRefreshToken(user.getId().toString());

    long expirationSeconds = jwtProperties.refreshTokenExpiration();
    Instant expiresAt = Instant.now().plusSeconds(expirationSeconds);

    // DB 장부 회전 (밀어내기)
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

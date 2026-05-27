package com.sprint.mission.discodeit.global.security.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.sprint.mission.discodeit.global.security.exception.JwtErrorCode;
import com.sprint.mission.discodeit.global.security.exception.JwtException;
import com.sprint.mission.discodeit.global.security.utils.DiscodeitAuthorityUtils;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class JwtTokenProvider {

  private static final String AUTHORITIES_KEY = "role";
  private final JwtProperties jwtProperties;
  private final JWSSigner signer;
  private final JWSVerifier verifier;
  private final JWSHeader header;

  public JwtTokenProvider(JwtProperties jwtProperties) throws JOSEException {
    this.jwtProperties = jwtProperties;
    byte[] secretBytes = jwtProperties.secretKey().getBytes(StandardCharsets.UTF_8);
    this.signer = new MACSigner(secretBytes);
    this.verifier = new MACVerifier(secretBytes);
    this.header = new JWSHeader(JWSAlgorithm.HS256);
  }

  public String generateAccessToken(Authentication authentication) {
    String authorities = DiscodeitAuthorityUtils.serializeAuthorities(
        authentication.getAuthorities());
    return buildToken(authentication.getName(), authorities, getAccessTokenExpiration());
  }

  public String generateRefreshToken(Authentication authentication) {
    return buildToken(authentication.getName(), null, getRefreshTokenExpiration());
  }

  public Authentication getAuthentication(String token) {
    SignedJWT signedJWT = getOrThrow(() -> SignedJWT.parse(token),
        new JwtException(JwtErrorCode.TOKEN_CANT_BE_PARSED));

    JWTClaimsSet claims = getOrThrow(signedJWT::getJWTClaimsSet,
        new JwtException(JwtErrorCode.PAYLOAD_CANT_BE_PARSED));

    String authClaim = getOrThrow(() -> claims.getStringClaim(AUTHORITIES_KEY),
        new JwtException(JwtErrorCode.ROLE_NOT_FOUND));

    if (!StringUtils.hasText(authClaim)) {
      throw new JwtException(JwtErrorCode.ROLE_NOT_FOUND);
    }

    Collection<? extends GrantedAuthority> authorities =
        DiscodeitAuthorityUtils.deserializeAuthorities(authClaim);
    UserDetails principal = new User(claims.getSubject(), "", authorities);
    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  public void validate(String token) {
    SignedJWT signedJWT = getOrThrow(() -> SignedJWT.parse(token),
        new JwtException(JwtErrorCode.TOKEN_CANT_BE_PARSED));

    boolean verified = getOrThrow(() -> signedJWT.verify(verifier),
        new JwtException(JwtErrorCode.INVALID_TOKEN));

    if (!verified) {
      throw new JwtException(JwtErrorCode.INVALID_TOKEN);
    }

    Date expirationTime = getOrThrow(() -> signedJWT.getJWTClaimsSet().getExpirationTime(),
        new JwtException(JwtErrorCode.PAYLOAD_CANT_BE_PARSED));

    if (expirationTime == null || new Date().after(expirationTime)) {
      throw new JwtException(JwtErrorCode.EXPIRED_TOKEN);
    }
  }

  private String buildToken(String subject, String authorities, long expirationMillis) {
    long now = System.currentTimeMillis();

    JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder()
        .subject(subject)
        .issueTime(new Date(now))
        .expirationTime(new Date(now + expirationMillis));

    if (StringUtils.hasText(authorities)) {
      builder.claim(AUTHORITIES_KEY, authorities);
    }

    SignedJWT signedJWT = new SignedJWT(header, builder.build());
    runOrThrow(() -> signedJWT.sign(signer), new JwtException(JwtErrorCode.INVALID_TOKEN));

    return signedJWT.serialize();
  }

  private <T> T getOrThrow(JwtCheckedExceptionSupplier<T> supplier, JwtException exception) {
    try {
      return supplier.get();
    } catch (Exception e) {
      log.error(exception.getMessage(), e);
      throw exception;
    }
  }

  private void runOrThrow(JwtCheckedExceptionRunnable runnable, JwtException exception) {
    try {
      runnable.run();
    } catch (Exception e) {
      log.error(exception.getMessage(), e);
      throw exception;
    }
  }

  @FunctionalInterface
  private interface JwtCheckedExceptionSupplier<T> {

    T get() throws Exception;
  }

  @FunctionalInterface
  private interface JwtCheckedExceptionRunnable {

    void run() throws Exception;
  }

  private long getAccessTokenExpiration() {
    return jwtProperties.accessTokenExpiration() * 1000;
  }

  private long getRefreshTokenExpiration() {
    return jwtProperties.refreshTokenExpiration() * 1000;
  }
}

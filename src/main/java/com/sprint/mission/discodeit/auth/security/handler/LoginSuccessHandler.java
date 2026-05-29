package com.sprint.mission.discodeit.auth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.discodeit.auth.dto.JwtLoginResponse;
import com.sprint.mission.discodeit.auth.security.DiscodeitUserDetails;
import com.sprint.mission.discodeit.auth.service.AuthService;
import com.sprint.mission.discodeit.global.security.jwt.JwtProperties;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  @Value("${discodeit.api-prefix}")
  private String apiPrefix;

  private final ObjectMapper objectMapper;
  private final JwtProperties jwtProperties;
  private final AuthService authService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    DiscodeitUserDetails userDetails = (DiscodeitUserDetails) authentication.getPrincipal();
    JwtLoginResponse result = authService.createJwtLogin(userDetails.getUserResponse());
    String json = objectMapper.writeValueAsString(result);
    response.addCookie(getRefreshTokenCookie(result.refreshToken()));
    response.getWriter().write(json);
  }

  private Cookie getRefreshTokenCookie(String token) {
    Cookie cookie = new Cookie("REFRESH_TOKEN", token);
    cookie.setHttpOnly(true);
    cookie.setSecure(false);
    cookie.setMaxAge((int) jwtProperties.refreshTokenExpiration());
    cookie.setPath(apiPrefix);
    return cookie;
  }
}

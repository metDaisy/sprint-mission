package com.sprint.mission.discodeit.auth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.discodeit.auth.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

@Component
@RequiredArgsConstructor
public class JWTLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

  private final ObjectMapper objectMapper;
  private final AuthService authService;

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    deleteRefreshToken(request, authentication);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    Map<String, String> result = Map.of("message", "logout success");
    response.getWriter().write(objectMapper.writeValueAsString(result));
  }

  private void deleteRefreshToken(HttpServletRequest request, Authentication authentication) {
    Cookie cookie = WebUtils.getCookie(request, "REFRESH_TOKEN");
    if (cookie == null) {
      return;
    }

  }
}

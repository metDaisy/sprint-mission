package com.sprint.mission.discodeit.auth.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.discodeit.dto.request.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

@RequiredArgsConstructor
public class JsonUserPasswordAuthentificationConverter implements AuthenticationConverter {

  private final ObjectMapper objectMapper;

  @Override
  public Authentication convert(HttpServletRequest request) {
    validateRequest(request);
    try {
      LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(),
          LoginRequest.class);
      return UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUsername(),
          loginRequest.getPassword());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void validateRequest(HttpServletRequest request) {
    if (!request.getMethod().equals(HttpMethod.POST.name()) || !request.getContentType().equals(
        MediaType.APPLICATION_JSON_VALUE)) {
      throw new AuthenticationServiceException("Unsupported authentication format");
    }
  }
}

package com.sprint.mission.discodeit.auth.controller;

import com.sprint.mission.discodeit.auth.controller.dto.JwtLoginResponse;
import com.sprint.mission.discodeit.auth.security.DiscodeitUserDetails;
import com.sprint.mission.discodeit.auth.service.AuthService;
import com.sprint.mission.discodeit.user.dto.request.RoleUpdateRequest;
import com.sprint.mission.discodeit.user.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @GetMapping("/csrf-token")
  public ResponseEntity<Void> csrfToken(CsrfToken token) {
    token.getToken();
    return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
  }

  @PutMapping("/role")
  public ResponseEntity<UserResponse> updateRole(@RequestBody RoleUpdateRequest request) {
    return ResponseEntity.status(HttpStatus.OK).body(authService.updateRole(request));
  }

  @PostMapping("/refresh")
  public ResponseEntity<JwtLoginResponse> refresh(
      @CookieValue(value = "REFRESH_TOKEN", required = false) String token) {
    JwtLoginResponse response = authService.reissue(token);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}

package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.dto.request.LoginRequest;
import com.sprint.mission.discodeit.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping(value = "/login")
  public ResponseEntity<UserDto> login(@RequestBody @Valid LoginRequest request) {
    return ResponseEntity.status(HttpStatus.OK).body(authService.login(request));
  }

  @GetMapping("/csrf-token")
  public ResponseEntity<Void> csrfToken(CsrfToken token) {
    return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
        .header(token.getHeaderName(), token.getToken()).build();
  }
}

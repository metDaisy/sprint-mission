package com.sprint.mission.discodeit.auth.infra.adapter;

import com.sprint.mission.discodeit.auth.domain.provider.UserRoleUpdateProvider;
import com.sprint.mission.discodeit.user.controller.dto.response.UserResponse;
import com.sprint.mission.discodeit.user.domain.entity.constant.UserRole;
import com.sprint.mission.discodeit.user.service.UserService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRoleUpdateAdapter implements UserRoleUpdateProvider {

  private final UserService service;

  @Override
  public UserResponse update(UUID userId, UserRole role) {
    return service.updateRole(userId, role);
  }
}

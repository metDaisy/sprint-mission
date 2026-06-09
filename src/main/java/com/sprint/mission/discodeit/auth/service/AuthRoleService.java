package com.sprint.mission.discodeit.auth.service;

import com.sprint.mission.discodeit.auth.domain.event.UserRoleUpdateEvent;
import com.sprint.mission.discodeit.auth.domain.provider.UserRoleUpdateProvider;
import com.sprint.mission.discodeit.user.controller.dto.request.RoleUpdateRequest;
import com.sprint.mission.discodeit.user.controller.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthRoleService {

  private final UserRoleUpdateProvider userRoleUpdateProvider;
  private final ApplicationEventPublisher eventPublisher;

  public UserResponse updateRole(RoleUpdateRequest request) {
    eventPublisher.publishEvent(new UserRoleUpdateEvent(request.getUserId()));
    return userRoleUpdateProvider.update(request.getUserId(), request.getRole());
  }
}

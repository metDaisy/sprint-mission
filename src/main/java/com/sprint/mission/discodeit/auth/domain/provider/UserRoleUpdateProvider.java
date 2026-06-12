package com.sprint.mission.discodeit.auth.domain.provider;

import com.sprint.mission.discodeit.user.presentation.dto.response.UserResponse;
import com.sprint.mission.discodeit.user.domain.entity.constant.UserRole;
import java.util.UUID;

public interface UserRoleUpdateProvider {

  UserResponse update(UUID userId, UserRole role);
}

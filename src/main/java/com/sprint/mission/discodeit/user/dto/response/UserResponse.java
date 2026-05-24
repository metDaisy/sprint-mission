package com.sprint.mission.discodeit.user.dto.response;

import com.sprint.mission.discodeit.auth.constant.DiscodeitRole;
import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.user.entity.User;
import java.io.Serializable;
import java.util.UUID;
import lombok.Builder;

/**
 * DTO for {@link User}
 */
@Builder
public record UserResponse(UUID id,
                           String username,
                           String email,
                           BinaryContentDto profile,
                           boolean online,
                           DiscodeitRole role) implements Serializable {

}

package com.sprint.mission.discodeit.dto.request;

import com.sprint.mission.discodeit.entity.User;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * DTO for {@link User}
 */
public record UserCreateRequest(@NotEmpty String username, @NotEmpty String email, @NotEmpty String password)
    implements Serializable {

}

package com.sprint.mission.discodeit.dto.request;

import com.sprint.mission.discodeit.entity.User;
import jakarta.validation.constraints.Email;
import java.io.Serializable;

/**
 * DTO for {@link User}
 */
public record UserUpdateRequest(String username, @Email String email, String password)
    implements Serializable {

}

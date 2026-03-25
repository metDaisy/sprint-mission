package com.sprint.mission.discodeit.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO for {@link com.sprint.mission.discodeit.entity.User}
 */
public record UserDto(@NotBlank String username, @Email String email, @NotBlank String password,
                      @NotNull BinaryContentDto profile, @NotNull UserStatusDto status) implements
    Serializable {

}

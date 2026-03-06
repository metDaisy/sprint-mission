package com.sprint.mission.discodeit.dto.user;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentServiceDTO.BinaryContentDto;

import java.util.UUID;

public interface UserServiceDTO {
    // todo: error log
    record UserResponse(UUID id, String username, String email, boolean online) {
    }

    record UserDto(UUID id, String username, String email, String password,
                   BinaryContentDto profile, boolean online) implements UserUpdateDto, UserCreateDto {
    }

    interface UserUpdateDto extends UserUniquenessDto {
        UUID id();

        String password();

        BinaryContentDto profile();
    }

    interface UserCreateDto extends UserUniquenessDto {
        String password();

        BinaryContentDto profile();
    }

    interface UserUniquenessDto {
        String username();

        String email();
    }
}

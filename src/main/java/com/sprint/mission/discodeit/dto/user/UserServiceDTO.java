package com.sprint.mission.discodeit.dto.user;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentServiceDTO.BinaryContentDto;
import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentServiceDTO.BinaryContentResponse;
import com.sprint.mission.discodeit.dto.user.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.user.request.UserUpdateRequest;
import lombok.Builder;

import java.util.UUID;

public interface UserServiceDTO {
    // todo: error log
    @Builder
    record UserResponse(UUID id, String username, String email, boolean online,
                        BinaryContentResponse profile) {
    }

    record UserDto(UUID id, String username, String email, String password,
                   BinaryContentDto profile, boolean online) implements UserUpdateDto, UserCreateDto {
        public UserDto(UserCreateRequest request, BinaryContentDto profileDto) {
            this(null, request.username(), request.email(), request.password(), profileDto, false);
        }

        public UserDto(UUID id, UserUpdateRequest request, BinaryContentDto profileDto) {
            this(id, request.username(), request.email(), request.password(), profileDto, false);
        }
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

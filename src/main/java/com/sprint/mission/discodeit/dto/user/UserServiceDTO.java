package com.sprint.mission.discodeit.dto.user;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentServiceDTO;
import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentServiceDTO.BinaryContentDto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

public interface UserServiceDTO {
    record UserUniquenessDto(String username, String email) {
    }

    @Builder
    record UserUpdateDto(String username, String email, String password, UUID profileId) {}

    record UserProfileImageDto(String fileName, byte[] data) {}

    // todo: error log
    @Builder
    record UserDto(UUID id, String username, String email, BinaryContentDto profile, boolean online) {
    }
}

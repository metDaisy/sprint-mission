package com.sprint.mission.discodeit.dto.binarycontent;

import lombok.Builder;

import java.util.UUID;

public interface BinaryContentServiceDTO {

    @Builder
    record BinaryContentDto(UUID id, String fileName, int size, String contentType) {
    }
}

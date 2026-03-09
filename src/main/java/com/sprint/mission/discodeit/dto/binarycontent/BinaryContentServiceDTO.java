package com.sprint.mission.discodeit.dto.binarycontent;

import lombok.Builder;

import java.util.UUID;

public interface BinaryContentServiceDTO {
    record BinaryContentResponse(UUID id, String fileName, Long size, String contentType) {
    }

    @Builder
    record BinaryContentDto(UUID id, String fileName, Long size, String contentType, byte[] bytes) {
    }
}

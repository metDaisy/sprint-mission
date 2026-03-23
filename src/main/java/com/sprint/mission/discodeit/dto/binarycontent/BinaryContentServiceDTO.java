package com.sprint.mission.discodeit.dto.binarycontent;

import com.sprint.mission.discodeit.common.exception.code.ErrorCode;
import com.sprint.mission.discodeit.common.exception.custom.APIException;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface BinaryContentServiceDTO {
    record BinaryContentResponse(UUID id, String fileName, Long size, String contentType) {
    }

    @Builder
    record BinaryContentDto(UUID id, String fileName, Long size, String contentType, byte[] bytes) {
        public static BinaryContentDto from(MultipartFile file) {
            try {
                return BinaryContentDto.builder()
                        .fileName(file.getOriginalFilename())
                        .size(file.getSize())
                        .contentType(file.getContentType())
                        .bytes(file.getBytes())
                        .build();
            } catch (IOException e) {
                throw new APIException(ErrorCode.FILE_CANT_READ, e.getMessage());
            }
        }
    }
}

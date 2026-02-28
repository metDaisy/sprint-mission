package com.sprint.mission.discodeit.dto.binarycontent.request;

import jakarta.validation.constraints.NotBlank;

public record BinaryContentCreateRequest(@NotBlank String fileName, byte[] data) {
}

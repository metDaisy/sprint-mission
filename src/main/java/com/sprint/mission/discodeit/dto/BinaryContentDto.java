package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.BinaryContent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.io.Serializable;

/**
 * DTO for {@link BinaryContent}
 */
public record BinaryContentDto(@NotBlank String fileName, @NotNull @Positive Long size,
                               @NotBlank String contentType, @NotNull byte[] bytes)
    implements Serializable {

}

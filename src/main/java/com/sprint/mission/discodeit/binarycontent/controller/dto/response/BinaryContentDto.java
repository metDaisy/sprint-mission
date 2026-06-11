package com.sprint.mission.discodeit.binarycontent.controller.dto.response;

import com.sprint.mission.discodeit.binarycontent.domain.entity.BinaryContent;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;

/**
 * DTO for {@link BinaryContent}
 */
@Builder
public record BinaryContentDto(UUID id,
                               Instant createdAt,
                               String fileName,
                               Long size,
                               String contentType
)
    implements Serializable {

}

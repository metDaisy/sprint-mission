package com.sprint.mission.discodeit.user.domain.payload;

import com.sprint.mission.discodeit.binarycontent.presentation.dto.response.BinaryContentDto;
import com.sprint.mission.discodeit.common.payload.marker.PayloadCreatedMarker;
import com.sprint.mission.discodeit.common.payload.marker.PayloadUpdatedMarker;
import com.sprint.mission.discodeit.user.domain.entity.constant.UserRole;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UserPayload(UUID id,
                          String username,
                          String email,
                          BinaryContentDto profile,
                          boolean online,
                          UserRole role) implements PayloadCreatedMarker, PayloadUpdatedMarker {

}

package com.sprint.mission.discodeit.message.domain.payload;

import com.sprint.mission.discodeit.common.payload.marker.PayloadUpdatedMarker;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;

@Builder
public record MessagePayloadUpdated(UUID id, Instant updatedAt, String content)
    implements PayloadUpdatedMarker {

}

package com.sprint.mission.discodeit.message.domain.payload;

import com.sprint.mission.discodeit.common.payload.marker.PayloadUpdatedMarker;
import java.util.UUID;

public record MessagePayloadUpdated(UUID id, String content)
    implements PayloadUpdatedMarker {

}

package com.sprint.mission.discodeit.notification.domain.payload;

import com.sprint.mission.discodeit.common.payload.marker.PayloadCreatedMarker;
import java.util.UUID;
import lombok.Builder;

@Builder
public record NotificationPayloadCreated(UUID id, String title, String content)
    implements PayloadCreatedMarker {

}

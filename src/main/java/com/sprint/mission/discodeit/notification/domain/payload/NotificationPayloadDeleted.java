package com.sprint.mission.discodeit.notification.domain.payload;

import com.sprint.mission.discodeit.common.payload.marker.PayloadDeletedMarker;
import java.util.UUID;

public record NotificationPayloadDeleted(UUID id) implements PayloadDeletedMarker {

}

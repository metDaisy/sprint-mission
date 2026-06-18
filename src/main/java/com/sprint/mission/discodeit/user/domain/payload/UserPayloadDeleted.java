package com.sprint.mission.discodeit.user.domain.payload;

import com.sprint.mission.discodeit.common.payload.marker.PayloadDeletedMarker;
import java.util.UUID;

public record UserPayloadDeleted(UUID id) implements PayloadDeletedMarker {

}

package com.sprint.mission.discodeit.channel.domain.payload;

import com.sprint.mission.discodeit.common.payload.marker.PayloadDeletedMarker;
import java.util.UUID;

public record ChannelPayloadDeleted(UUID id) implements PayloadDeletedMarker {

}

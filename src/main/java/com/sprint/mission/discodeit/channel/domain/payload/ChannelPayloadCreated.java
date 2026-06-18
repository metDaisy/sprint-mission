package com.sprint.mission.discodeit.channel.domain.payload;

import com.sprint.mission.discodeit.common.payload.marker.PayloadCreatedMarker;
import java.util.UUID;
import lombok.Builder;

@Builder
public record ChannelPayloadCreated(UUID id, String name, String description)
    implements PayloadCreatedMarker {

}

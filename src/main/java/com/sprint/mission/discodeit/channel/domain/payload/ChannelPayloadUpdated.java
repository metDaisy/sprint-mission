package com.sprint.mission.discodeit.channel.domain.payload;

import com.sprint.mission.discodeit.common.payload.marker.PayloadUpdatedMarker;
import java.util.UUID;
import lombok.Builder;

@Builder
public record ChannelPayloadUpdated(UUID id, String name, String description)
    implements PayloadUpdatedMarker {

}

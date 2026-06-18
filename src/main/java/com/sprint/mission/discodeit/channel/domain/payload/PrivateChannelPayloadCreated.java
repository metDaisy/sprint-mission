package com.sprint.mission.discodeit.channel.domain.payload;

import com.sprint.mission.discodeit.common.payload.marker.PayloadCreatedMarker;
import java.util.Collection;
import java.util.UUID;
import lombok.Builder;

@Builder
public record PrivateChannelPayloadCreated(UUID id, Collection<UUID> participantIds)
    implements PayloadCreatedMarker {

}

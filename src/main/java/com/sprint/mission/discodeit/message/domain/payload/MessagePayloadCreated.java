package com.sprint.mission.discodeit.message.domain.payload;

import com.sprint.mission.discodeit.common.payload.marker.PayloadCreatedMarker;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record MessagePayloadCreated(UUID id, String content, List<UUID> attachmentIds)
    implements PayloadCreatedMarker {

}

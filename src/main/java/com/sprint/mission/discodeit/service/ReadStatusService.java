package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.ReadStatusServiceDTO.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.ReadStatusServiceDTO.ReadStatusResponse;
import com.sprint.mission.discodeit.dto.ReadStatusServiceDTO.ReadStatusUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface ReadStatusService extends DomainService<ReadStatusResponse, ReadStatusCreateRequest, ReadStatusUpdateRequest> {
    List<ReadStatusResponse> findAllByUserId(UUID userId);

    ReadStatusResponse find(UUID channelId, UUID userId);
}

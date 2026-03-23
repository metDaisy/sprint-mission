package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.readstatus.ReadStatusServiceDTO.ReadStatusCreateDto;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusServiceDTO.ReadStatusResponse;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusServiceDTO.ReadStatusUpdateDto;

import java.util.List;
import java.util.UUID;

public interface ReadStatusService extends DomainService<ReadStatusResponse, ReadStatusCreateDto, ReadStatusUpdateDto> {
    List<ReadStatusResponse> findAllByUserId(UUID userId);

    ReadStatusResponse find(UUID userId, UUID channelId);
}

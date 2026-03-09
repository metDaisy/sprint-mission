package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentServiceDTO;
import com.sprint.mission.discodeit.dto.binarycontent.request.BinaryContentCreateRequest;

import java.util.List;
import java.util.UUID;

public interface BinaryContentService {
    BinaryContentServiceDTO.BinaryContentResponse create(BinaryContentCreateRequest request);

    BinaryContentServiceDTO.BinaryContentResponse find(UUID id);

    void delete(UUID id);

    List<BinaryContentServiceDTO.BinaryContentResponse> findAllByIdIn(List<UUID> ids);
}

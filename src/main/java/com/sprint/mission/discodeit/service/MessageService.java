package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.message.MessageServiceDTO.MessageCreateDto;
import com.sprint.mission.discodeit.dto.message.MessageServiceDTO.MessageResponse;
import com.sprint.mission.discodeit.dto.message.MessageServiceDTO.MessageUpdateDto;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    List<MessageResponse> findAllByChannelId(UUID channelId);

    MessageResponse create(MessageCreateDto dto);

    MessageResponse update(MessageUpdateDto dto);

    void delete(UUID id);
}

package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.ChannelDto;
import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.ChannelResponse;
import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.PrivateChannelCreateDto;
import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.PublicChannelCreateDto;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    ChannelResponse find(UUID id);

    List<ChannelResponse> findAllByUserId(UUID userId);

    ChannelResponse createPublic(PublicChannelCreateDto dto);

    ChannelResponse createPrivate(PrivateChannelCreateDto dto);

    ChannelResponse update(ChannelDto dto);

    void delete(UUID id);
}

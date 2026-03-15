package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.ChannelDto;
import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.ChannelResponse;
import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.PrivateChannelCreateDto;
import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.PublicChannelCreateDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.mapper.config.GlobalMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class)
public interface ChannelMapper extends BaseMapper<ChannelDto, Channel, ChannelResponse> {
    @Override
    ChannelResponse toResponse(Channel entity);

    Channel fromDto(PublicChannelCreateDto dto);

    Channel fromDto(PrivateChannelCreateDto dto);
}

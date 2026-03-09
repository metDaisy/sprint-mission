package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.ChannelDto;
import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.ChannelResponse;
import com.sprint.mission.discodeit.dto.channel.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.channel.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.channel.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.mapper.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(config = GlobalMapperConfig.class)
public interface ChannelMapper extends BaseMapper<ChannelDto, Channel, ChannelResponse> {
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    ChannelDto toDtoFromRequest(PublicChannelCreateRequest request);

    ChannelDto toDtoFromRequest(PrivateChannelCreateRequest request);

    ChannelDto toDtoFromRequest(UUID id, PublicChannelUpdateRequest request);
}

package com.sprint.mission.discodeit.channel.application.mapper;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.channel.presentation.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.channel.presentation.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.channel.presentation.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.common.mapper.GenericDomainMapper;
import com.sprint.mission.discodeit.common.mapper.config.GlobalMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class)
public interface ChannelDomainMapper extends GenericDomainMapper<PublicChannelUpdateRequest, Channel> {

  Channel toEntityFrom(PublicChannelCreateRequest request);

  Channel toEntityFrom(PrivateChannelCreateRequest request);
}

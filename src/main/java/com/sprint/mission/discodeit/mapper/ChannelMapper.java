package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.PrivateChannelDto;
import com.sprint.mission.discodeit.dto.PublicChannelDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.mapper.config.GlobalMapperConfig;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = GlobalMapperConfig.class, uses = {ReadStatusMapper.class})
public interface ChannelMapper {

  Channel toEntity(PublicChannelDto publicChannelDto);

  PublicChannelDto toPublicDto(Channel channel);

  Channel partialUpdate(
      PublicChannelDto publicChannelDto, @MappingTarget Channel channel);

  Channel toEntity(PrivateChannelDto privateChannelDto);

  @AfterMapping
  default void linkReadStatuses(@MappingTarget Channel channel) {
    channel.getReadStatuses().forEach(readStatus -> readStatus.setChannel(channel));
  }

  PrivateChannelDto toPrivateDto(Channel channel);

  Channel partialUpdate(
      PrivateChannelDto privateChannelDto, @MappingTarget Channel channel);

}

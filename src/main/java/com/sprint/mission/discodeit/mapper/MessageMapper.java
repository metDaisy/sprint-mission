package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.MessageDto;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.mapper.config.GlobalMapperConfig;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = GlobalMapperConfig.class,
    uses = {ChannelMapper.class, UserMapper.class, BinaryContentMapper.class})
public interface MessageMapper {

  Message toEntity(MessageDto messageDto);

  MessageDto toDto(Message message);

  Message partialUpdate(
      MessageDto messageDto, @MappingTarget Message message);
}

package com.sprint.mission.discodeit.message.controller.mapper;

import com.sprint.mission.discodeit.common.mapper.BaseMapper;
import com.sprint.mission.discodeit.common.mapper.PageResponseMapper;
import com.sprint.mission.discodeit.common.mapper.config.GlobalMapperConfig;
import com.sprint.mission.discodeit.message.controller.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.message.controller.dto.response.MessageResponse;
import com.sprint.mission.discodeit.message.domain.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = GlobalMapperConfig.class)
public interface MessageMapper extends BaseMapper<Message, MessageResponse>,
    PageResponseMapper<Message, MessageResponse> {

  @Override
  @Mapping(target = "channelId", source = "channel.id")
  MessageResponse toDto(Message entity);

  Message partialUpdate(MessageUpdateRequest request, @MappingTarget Message message);
}

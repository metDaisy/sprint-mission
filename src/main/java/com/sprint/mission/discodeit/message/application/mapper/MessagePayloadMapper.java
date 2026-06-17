package com.sprint.mission.discodeit.message.application.mapper;

import com.sprint.mission.discodeit.common.mapper.PayloadMapper;
import com.sprint.mission.discodeit.common.mapper.config.GlobalMapperConfig;
import com.sprint.mission.discodeit.message.domain.entity.Message;
import com.sprint.mission.discodeit.message.domain.payload.MessagePayloadCreated;
import com.sprint.mission.discodeit.message.domain.payload.MessagePayloadDeleted;
import com.sprint.mission.discodeit.message.domain.payload.MessagePayloadUpdated;
import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class)
public abstract class MessagePayloadMapper extends PayloadMapper<Message> {

  @Override
  protected abstract MessagePayloadCreated toCreated(Message entity);

  @Override
  protected abstract MessagePayloadUpdated toUpdated(Message entity);

  @Override
  protected abstract MessagePayloadDeleted toDeleted(Message entity);
}

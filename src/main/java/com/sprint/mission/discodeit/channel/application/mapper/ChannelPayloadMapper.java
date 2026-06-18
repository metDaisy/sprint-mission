package com.sprint.mission.discodeit.channel.application.mapper;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.channel.domain.payload.ChannelPayloadCreated;
import com.sprint.mission.discodeit.channel.domain.payload.ChannelPayloadDeleted;
import com.sprint.mission.discodeit.channel.domain.payload.ChannelPayloadUpdated;
import com.sprint.mission.discodeit.channel.domain.payload.PrivateChannelPayloadCreated;
import com.sprint.mission.discodeit.common.mapper.PayloadMapper;
import com.sprint.mission.discodeit.common.mapper.config.GlobalMapperConfig;
import com.sprint.mission.discodeit.user.domain.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GlobalMapperConfig.class)
public abstract class ChannelPayloadMapper extends PayloadMapper<Channel> {

  @Override
  protected abstract ChannelPayloadCreated toCreated(Channel entity);

  @Override
  protected abstract ChannelPayloadUpdated toUpdated(Channel entity);

  @Override
  protected abstract ChannelPayloadDeleted toDeleted(Channel entity);

  @Mapping(target = "id", source = "entity.id")
  @Mapping(target = "participantIds", source = "participants.id")
  public abstract PrivateChannelPayloadCreated toCreated(Channel entity, List<User> participants);
}

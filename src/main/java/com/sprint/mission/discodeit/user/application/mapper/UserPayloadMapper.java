package com.sprint.mission.discodeit.user.application.mapper;

import com.sprint.mission.discodeit.common.mapper.PayloadMapper;
import com.sprint.mission.discodeit.common.mapper.config.GlobalMapperConfig;
import com.sprint.mission.discodeit.user.domain.entity.User;
import com.sprint.mission.discodeit.user.domain.payload.UserPayload;
import com.sprint.mission.discodeit.user.domain.payload.UserPayloadDeleted;
import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class)
public abstract class UserPayloadMapper extends PayloadMapper<User> {

  @Override
  protected abstract UserPayload toCreated(User entity);

  @Override
  protected abstract UserPayload toUpdated(User entity);

  @Override
  protected abstract UserPayloadDeleted toDeleted(User entity);
}

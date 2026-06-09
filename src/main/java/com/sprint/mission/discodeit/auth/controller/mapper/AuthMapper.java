package com.sprint.mission.discodeit.auth.controller.mapper;

import com.sprint.mission.discodeit.common.mapper.config.GlobalMapperConfig;
import com.sprint.mission.discodeit.user.controller.dto.response.UserResponse;
import com.sprint.mission.discodeit.user.controller.mapper.UserMapper;
import com.sprint.mission.discodeit.user.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class, uses = UserMapper.class)
public interface AuthMapper {

  UserResponse toUserResponse(User user);
}

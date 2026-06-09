package com.sprint.mission.discodeit.user.controller.mapper;

import com.sprint.mission.discodeit.binarycontent.controller.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.common.mapper.BaseMapper;
import com.sprint.mission.discodeit.common.mapper.config.GlobalMapperConfig;
import com.sprint.mission.discodeit.user.controller.dto.response.UserResponse;
import com.sprint.mission.discodeit.user.domain.entity.User;
import org.mapstruct.Mapper;


@Mapper(config = GlobalMapperConfig.class, uses = BinaryContentMapper.class)
public interface UserMapper extends BaseMapper<User, UserResponse> {

}

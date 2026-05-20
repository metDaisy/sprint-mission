package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.dto.request.RoleUpdateRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.config.GlobalMapperConfig;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(config = GlobalMapperConfig.class, uses = BinaryContentMapper.class)
public interface UserMapper extends BaseMapper<User, UserDto> {


  @Mapping(target = "status", source = "status")
  User toEntityFrom(UserCreateRequest request, UserStatus status, BinaryContent profile);

  @AfterMapping
  default void linkStatus(@MappingTarget User user) {
    UserStatus status = user.getStatus();
    if (status != null) {
      status.setUser(user);
    }
  }

  @Mapping(target = "status", ignore = true)
  User partialUpdate(UserUpdateRequest request, BinaryContent profile, @MappingTarget User user);

  User partialUpdate(RoleUpdateRequest request, @MappingTarget User user);
}

package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.config.GlobalMapperConfig;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(config = GlobalMapperConfig.class,
    uses = {BinaryContentMapper.class, UserStatusMapper.class})
public interface UserMapper {

  User toEntity(UserDto userDto);

  @AfterMapping
  default void linkStatus(@MappingTarget User user) {
    UserStatus status = user.getStatus();
    if (status != null) {
      status.setUser(user);
    }
  }

  UserDto toDto(User user);

  User partialUpdate(
      UserDto userDto, @MappingTarget User user);
}

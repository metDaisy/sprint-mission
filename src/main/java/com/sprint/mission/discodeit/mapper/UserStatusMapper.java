package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.UserStatusDto;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = GlobalMapperConfig.class, uses = {UserMapper.class})
public interface UserStatusMapper {

  UserStatus toEntity(UserStatusDto userStatusDto);

  UserStatusDto toDto(UserStatus userStatus);

  UserStatus partialUpdate(
      UserStatusDto userStatusDto, @MappingTarget UserStatus userStatus);
}

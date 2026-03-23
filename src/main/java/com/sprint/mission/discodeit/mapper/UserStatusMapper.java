package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.userstatus.UserStatusServiceDTO.UserStatusDto;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusServiceDTO.UserStatusResponse;
import com.sprint.mission.discodeit.dto.userstatus.request.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(config = GlobalMapperConfig.class)
public interface UserStatusMapper extends BaseMapper<UserStatusDto, UserStatus, UserStatusResponse> {
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "lastActiveAt", source = "request.datetime")
    UserStatusDto toEntity(UUID userId, UserStatusUpdateRequest request);

    @Override
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "lastActiveAt")
    UserStatusResponse toResponse(UserStatus entity);
}

package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.userstatus.UserStatusServiceDTO.UserStatusDto;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusServiceDTO.UserStatusResponse;
import com.sprint.mission.discodeit.dto.userstatus.request.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.config.GlobalMapperConfig;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(config = GlobalMapperConfig.class)
public interface UserStatusMapper extends BaseMapper<UserStatusDto, UserStatus, UserStatusResponse> {
    UserStatusDto toEntity(UUID userId, UserStatusUpdateRequest request);
}

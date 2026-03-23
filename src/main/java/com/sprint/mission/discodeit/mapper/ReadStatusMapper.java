package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.readstatus.ReadStatusServiceDTO.ReadStatusDto;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusServiceDTO.ReadStatusResponse;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.mapper.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GlobalMapperConfig.class)
public interface ReadStatusMapper extends BaseMapper<ReadStatusDto, ReadStatus, ReadStatusResponse> {
    @Override
    @Mapping(target = "lastReadAt")
    ReadStatusResponse toResponse(ReadStatus entity);
}

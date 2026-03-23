package com.sprint.mission.discodeit.mapper.config;

import com.sprint.mission.discodeit.mapper.common.TimeMapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@MapperConfig(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = TimeMapper.class)
public interface GlobalMapperConfig {
}

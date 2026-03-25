package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.ReadStatusDto;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.mapper.config.GlobalMapperConfig;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(config = GlobalMapperConfig.class, uses = {UserMapper.class})
public interface ReadStatusMapper {

  ReadStatus toEntity(ReadStatusDto readStatusDto);

  ReadStatusDto toDto(ReadStatus readStatus);

  ReadStatus partialUpdate(
      ReadStatusDto readStatusDto, @MappingTarget ReadStatus readStatus);
}

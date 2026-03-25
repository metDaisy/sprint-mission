package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.mapper.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = GlobalMapperConfig.class)
public interface BinaryContentMapper {

  BinaryContent toEntity(BinaryContentDto binaryContentDto);

  BinaryContentDto toDto(BinaryContent binaryContent);

  BinaryContent partialUpdate(
      BinaryContentDto binaryContentDto, @MappingTarget BinaryContent binaryContent);
}

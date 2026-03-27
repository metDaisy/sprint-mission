package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.mapper.config.GlobalMapperConfig;
import java.io.IOException;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

@Mapper(config = GlobalMapperConfig.class)
public interface BinaryContentMapper {

  BinaryContent toEntityFrom(MultipartFile profile) throws IOException;

  BinaryContentDto toDto(BinaryContent binaryContent);
}

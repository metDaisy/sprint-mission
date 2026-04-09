package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.mapper.config.GlobalMapperConfig;
import java.io.IOException;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

@Mapper(config = GlobalMapperConfig.class)
public interface BinaryContentMapper extends BaseMapper<BinaryContent, BinaryContentDto> {

  @Mapping(target = "fileName", source = "originalFilename")
  BinaryContent toEntityFrom(MultipartFile profile);

  default List<BinaryContent> toEntityFrom(List<MultipartFile> profiles) {
    if (profiles == null) {
      return List.of();
    }
    return doMapping(profiles);
  }

  List<BinaryContent> doMapping(List<MultipartFile> profiles);
}

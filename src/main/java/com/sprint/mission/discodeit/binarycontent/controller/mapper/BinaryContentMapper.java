package com.sprint.mission.discodeit.binarycontent.controller.mapper;

import com.sprint.mission.discodeit.binarycontent.controller.dto.response.BinaryContentDto;
import com.sprint.mission.discodeit.binarycontent.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.common.api.request.FileUploadRequest;
import com.sprint.mission.discodeit.common.mapper.BaseMapper;
import com.sprint.mission.discodeit.common.mapper.config.GlobalMapperConfig;
import java.util.List;
import java.util.Optional;
import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class)
public interface BinaryContentMapper extends BaseMapper<BinaryContent, BinaryContentDto> {

  default Optional<BinaryContent> toEntityFrom(Optional<FileUploadRequest> file) {
    return file.map(this::toEntityFrom);
  }

  List<BinaryContent> toEntityFrom(List<FileUploadRequest> files);

  BinaryContent toEntityFrom(FileUploadRequest dto);

}

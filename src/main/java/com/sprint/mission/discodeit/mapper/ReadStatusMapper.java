package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.common.utils.ProxyResolver;
import com.sprint.mission.discodeit.dto.ReadStatusDto;
import com.sprint.mission.discodeit.dto.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.mapper.config.GlobalMapperConfig;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = GlobalMapperConfig.class, uses = ProxyResolver.class)
public interface ReadStatusMapper extends BaseMapper<ReadStatus, ReadStatusDto> {

  ReadStatus toEntityFrom(ReadStatusCreateRequest request);

  ReadStatus partialUpdate(ReadStatusUpdateRequest request, @MappingTarget ReadStatus readStatus);
}

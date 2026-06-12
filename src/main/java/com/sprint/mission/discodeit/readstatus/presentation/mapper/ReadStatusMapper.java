package com.sprint.mission.discodeit.readstatus.presentation.mapper;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.common.mapper.GenericMapper;
import com.sprint.mission.discodeit.common.mapper.config.GlobalMapperConfig;
import com.sprint.mission.discodeit.readstatus.presentation.dto.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.readstatus.presentation.dto.response.ReadStatusResponse;
import com.sprint.mission.discodeit.readstatus.domain.entity.ReadStatus;
import com.sprint.mission.discodeit.user.domain.entity.User;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = GlobalMapperConfig.class)
public interface ReadStatusMapper extends GenericMapper<ReadStatus, ReadStatusResponse> {

  @Override
  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "channelId", source = "channel.id")
  ReadStatusResponse toDto(ReadStatus entity);

  List<ReadStatus> toEntityFrom(@Context Channel channel, List<User> participants);

  ReadStatus toEntityFrom(User user, @Context Channel channel);

  @AfterMapping
  default void setChannel(@MappingTarget ReadStatus.ReadStatusBuilder builder,
      @Context Channel channel) {
    builder.channel(channel);
  }

  ReadStatus partialUpdate(ReadStatusUpdateRequest request, @MappingTarget ReadStatus readStatus);
}

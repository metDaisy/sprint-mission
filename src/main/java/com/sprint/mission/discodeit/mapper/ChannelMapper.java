package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.ChannelDto;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.config.GlobalMapperConfig;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = GlobalMapperConfig.class)
public interface ChannelMapper {

  Channel toEntityFrom(PublicChannelCreateRequest request);

  Channel toEntityFrom(ChannelType type, @Context List<User> participants);

  @AfterMapping
  default void afterMapping(@MappingTarget Channel channel, @Context List<User> participants) {
    if (participants == null || participants.isEmpty()) {
      return;
    }
    channel.addParticipants(participants);
  }

  ChannelDto toDto(Channel channel);

  List<ChannelDto> toDto(List<Channel> channels);

  Channel partialUpdate(PublicChannelUpdateRequest request, @MappingTarget Channel channel);
}

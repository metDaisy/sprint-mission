package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.ChannelDto;
import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.config.GlobalMapperConfig;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = GlobalMapperConfig.class, uses = UserMapper.class)
public interface ChannelMapper extends BaseMapper<Channel, ChannelDto> {

  Channel toEntityFrom(PublicChannelCreateRequest request);

  Channel toEntityFrom(ChannelType type, @Context List<User> participants);

  @AfterMapping
  default void afterMapping(@MappingTarget Channel channel, @Context List<User> participants) {
    if (participants == null || participants.isEmpty()) {
      return;
    }
    channel.addParticipants(participants);
  }

  @Mapping(target = ".", source = "user")
  UserDto readStatusToUserDto(ReadStatus readStatus);

  @Mapping(target = "participants", source = "readStatuses")
  ChannelDto toDto(Channel channel);

  Channel partialUpdate(PublicChannelUpdateRequest request, @MappingTarget Channel channel);
}

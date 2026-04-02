package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.common.utils.ProxyResolver;
import com.sprint.mission.discodeit.dto.MessageDto;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.mapper.config.GlobalMapperConfig;
import java.io.IOException;
import java.util.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.web.multipart.MultipartFile;

@Mapper(config = GlobalMapperConfig.class, uses = {BinaryContentMapper.class, ProxyResolver.class})
public interface MessageMapper extends BaseMapper<Message, MessageDto> {

  @Mapping(target = "channel", source = "request.channelId")
  @Mapping(target = "author", source = "request.authorId")
  Message toEntityFrom(MessageCreateRequest request, Collection<MultipartFile> attachments)
      throws IOException;

  Message partialUpdate(MessageUpdateRequest request, @MappingTarget Message message);
}

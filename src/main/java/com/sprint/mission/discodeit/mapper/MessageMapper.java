package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentServiceDTO.BinaryContentDto;
import com.sprint.mission.discodeit.dto.message.MessageServiceDTO.MessageDto;
import com.sprint.mission.discodeit.dto.message.MessageServiceDTO.MessageResponse;
import com.sprint.mission.discodeit.dto.message.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.message.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.mapper.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Mapper(config = GlobalMapperConfig.class)
public interface MessageMapper extends BaseMapper<MessageDto, Message, MessageResponse> {
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    BinaryContentMapper attachmentMapper = Mappers.getMapper(BinaryContentMapper.class);

    default MessageDto toDtoFromRequest(MessageCreateRequest request, List<MultipartFile> attachments) {
        List<BinaryContentDto> attachmentDto = attachments.stream().map(attachmentMapper::toDtoFromFile).toList();
        return MessageDto.builder()
                .authorId(request.authorId())
                .channelId(request.channelId())
                .content(request.content())
                .attachments(attachmentDto)
                .build();
    }

    MessageDto toDtoFromRequest(UUID messageId, MessageUpdateRequest request);
}

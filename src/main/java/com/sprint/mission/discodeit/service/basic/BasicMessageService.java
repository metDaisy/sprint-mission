package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.common.exception.code.ErrorCode;
import com.sprint.mission.discodeit.common.exception.custom.APIException;
import com.sprint.mission.discodeit.dto.message.MessageServiceDTO.AuthorAndChannelId;
import com.sprint.mission.discodeit.dto.message.MessageServiceDTO.MessageCreateDto;
import com.sprint.mission.discodeit.dto.message.MessageServiceDTO.MessageResponse;
import com.sprint.mission.discodeit.dto.message.MessageServiceDTO.MessageUpdateDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicMessageService extends BasicDomainService<Message> implements MessageService {
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository attachmentRepository;
    private final MessageMapper messageMapper;

    @Override
    public MessageResponse create(MessageCreateDto dto) {
        ensureUserAndChannelExist(dto);
        List<BinaryContent> attachments = dto.attachments().stream()
                .map(BinaryContent::new)
                .map(attachmentRepository::save)
                .toList();
        User user = getOrThrow(dto.authorId(), userRepository::findById, () -> new APIException(ErrorCode.USERID_NOT_FOUND, dto.authorId()));
        Channel channel = getOrThrow(dto.channelId(), channelRepository::findById, () -> new APIException(ErrorCode.CHANNELID_NOT_FOUND, dto.channelId()));
        Message message = Message.builder()
                .content(dto.content())
                .author(user)
                .channel(channel)
                .attachments(attachments)
                .build();
        messageRepository.save(message);
        return messageMapper.toResponse(message);
    }

    @Override
    public List<MessageResponse> findAllByChannelId(UUID channelId) {
        return messageRepository.findAllByChannelId(channelId)
                .stream()
                .map(messageMapper::toResponse)
                .toList();
    }

    @Override
    public MessageResponse update(MessageUpdateDto dto) {
        Message message = findById(dto.id());
        message.update(dto);
        messageRepository.save(message);
        return messageMapper.toResponse(message);
    }

    @Override
    public void delete(UUID id) {
        deleteByIdOrThrow(id, messageRepository, new APIException(ErrorCode.MESSAGEID_NOT_FOUND, id));
    }

    @Override
    protected Message findById(UUID id) {
        return getOrThrow(id, messageRepository::findById,
                () -> new APIException(ErrorCode.MESSAGEID_NOT_FOUND, id));
    }

    private void ensureUserAndChannelExist(AuthorAndChannelId dto) {
        ensure(dto.authorId(), userRepository::existsById, id -> new APIException(ErrorCode.USERID_NOT_FOUND, id));
        ensure(dto.channelId(), channelRepository::existsById, id -> new APIException(ErrorCode.CHANNELID_NOT_FOUND, id));
    }
}

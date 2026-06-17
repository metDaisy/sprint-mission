package com.sprint.mission.discodeit.message.application.service;

import com.sprint.mission.discodeit.binarycontent.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.common.api.response.PageResponse;
import com.sprint.mission.discodeit.common.support.DomainServiceSupport;
import com.sprint.mission.discodeit.global.log.ServiceLogAround;
import com.sprint.mission.discodeit.message.application.mapper.MessagePayloadMapper;
import com.sprint.mission.discodeit.message.domain.entity.Message;
import com.sprint.mission.discodeit.message.domain.event.MessageCreatedEvent;
import com.sprint.mission.discodeit.message.domain.exception.MessageErrorCode;
import com.sprint.mission.discodeit.message.domain.exception.MessageException;
import com.sprint.mission.discodeit.message.domain.payload.MessagePayloadCreated;
import com.sprint.mission.discodeit.message.domain.payload.MessagePayloadUpdated;
import com.sprint.mission.discodeit.message.domain.provider.MessageBinaryContentResolver;
import com.sprint.mission.discodeit.message.domain.provider.MessageChannelResolver;
import com.sprint.mission.discodeit.message.domain.provider.MessageNotifier;
import com.sprint.mission.discodeit.message.domain.provider.MessageUserResolver;
import com.sprint.mission.discodeit.message.infra.repository.MessageRepository;
import com.sprint.mission.discodeit.message.presentation.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.message.presentation.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.message.presentation.dto.response.MessageResponse;
import com.sprint.mission.discodeit.message.presentation.mapper.MessageMapper;
import com.sprint.mission.discodeit.user.domain.entity.User;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

  private final MessageRepository repository;
  private final MessageMapper mapper;
  private final MessageChannelResolver channelResolver;
  private final MessageUserResolver userResolver;
  private final MessageBinaryContentResolver binaryContentResolver;
  private final ApplicationEventPublisher eventPublisher;
  private final MessageNotifier notifier;
  private final MessagePayloadMapper payloadMapper;

  @ServiceLogAround
  public MessageResponse create(MessageCreateRequest request) {
    User author = userResolver.getProxyOrThrow(request.getAuthorId());
    Channel channel = channelResolver.getProxyOrThrow(request.getChannelId());
    List<BinaryContent> attachments = binaryContentResolver.getProxyOrThrow(
        request.getAttachmentIds());

    Message message = Message.builder()
        .content(request.getContent())
        .channel(channel)
        .author(author)
        .attachments(attachments)
        .build();
    repository.save(message);
    eventPublisher.publishEvent(
        new MessageCreatedEvent(request.getAuthorId(), request.getChannelId(),
            request.getContent()));
    notifier.notifyCreated(request.getChannelId(),
        payloadMapper.toDto(message, MessagePayloadCreated.class));
    return mapper.toDto(message);
  }

  @ServiceLogAround
  @Transactional(readOnly = true)
  public PageResponse<MessageResponse> findSliceByChannelId(UUID channelId, Pageable pageable) {
    return mapper.fromSlice(repository.findSliceByChannel_Id(channelId, pageable));
  }

  @ServiceLogAround
  public MessageResponse update(UUID id, MessageUpdateRequest request) {
    Message message = findById(id);
    mapper.partialUpdate(request, message);
    notifier.notifyUpdated(message.getChannel().getId(),
        payloadMapper.toDto(message, MessagePayloadUpdated.class));
    return mapper.toDto(message);
  }

  @ServiceLogAround
  public void delete(UUID id) {
    DomainServiceSupport.deleteOrThrow(id, repository,
        messageId -> new MessageException(MessageErrorCode.MESSAGEID_NOT_FOUND, messageId));
    notifier.notifyDeleted(id);
  }

  private Message findById(UUID id) {
    return DomainServiceSupport.getOrThrow(id, repository::findById,
        messageId -> new MessageException(MessageErrorCode.MESSAGEID_NOT_FOUND, messageId));
  }
}

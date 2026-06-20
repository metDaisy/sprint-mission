package com.sprint.mission.discodeit.channel.application.service;

import com.sprint.mission.discodeit.channel.application.mapper.ChannelDomainMapper;
import com.sprint.mission.discodeit.channel.application.mapper.ChannelPayloadMapper;
import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.channel.domain.event.ReadStatusCreatedEvent;
import com.sprint.mission.discodeit.channel.domain.exception.ChannelErrorCode;
import com.sprint.mission.discodeit.channel.domain.exception.ChannelException;
import com.sprint.mission.discodeit.channel.domain.provider.ChannelNotifier;
import com.sprint.mission.discodeit.channel.domain.provider.ChannelUserResolver;
import com.sprint.mission.discodeit.channel.domain.repository.ChannelQueryRepository;
import com.sprint.mission.discodeit.channel.domain.repository.ChannelRepository;
import com.sprint.mission.discodeit.channel.infra.repository.qdsl.dto.ChannelDetailDto;
import com.sprint.mission.discodeit.channel.presentation.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.channel.presentation.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.channel.presentation.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.common.payload.marker.PayloadCreatedMarker;
import com.sprint.mission.discodeit.common.payload.marker.PayloadDeletedMarker;
import com.sprint.mission.discodeit.common.payload.marker.PayloadUpdatedMarker;
import com.sprint.mission.discodeit.common.support.DomainServiceSupport;
import com.sprint.mission.discodeit.global.log.ServiceLogAround;
import com.sprint.mission.discodeit.user.domain.entity.User;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChannelService {

  private final ChannelRepository repository;
  private final ChannelQueryRepository queryRepository;
  private final ChannelDomainMapper domainMapper;
  private final ApplicationEventPublisher eventPublisher;
  private final ChannelUserResolver userProvider;
  private final ChannelNotifier notifier;
  private final ChannelPayloadMapper payloadMapper;

  @ServiceLogAround
  public Channel createPublic(PublicChannelCreateRequest request) {
    Channel channel = domainMapper.toEntityFrom(request);
    repository.save(channel);
    notifier.notifyCreated(payloadMapper.toDto(channel, PayloadCreatedMarker.class));
    return channel;
  }

  @ServiceLogAround
  public ChannelDetailDto createPrivate(PrivateChannelCreateRequest request) {
    Channel channel = domainMapper.toEntityFrom(request);
    repository.save(channel);
    List<User> participants = userProvider.getOrThrow(request.getParticipantIds());
    eventPublisher.publishEvent(
        new ReadStatusCreatedEvent(channel.getId(), request.getParticipantIds(), true));
    notifier.notifyCreated(payloadMapper.toCreated(channel, participants));
    return new ChannelDetailDto(channel, null, participants);
  }

  @ServiceLogAround
  @Transactional(readOnly = true)
  public ChannelDetailDto find(UUID id) {
    return findByIdWithDetail(id);
  }

  @ServiceLogAround
  @Transactional(readOnly = true)
  public List<ChannelDetailDto> findAllByUserId(UUID userId) {
    return queryRepository.findVisibleChannelDetails(userId);
  }

  @ServiceLogAround
  public ChannelDetailDto update(UUID id, PublicChannelUpdateRequest request) {
    ChannelDetailDto channelDetail = findByIdWithDetail(id);
    Channel channel = channelDetail.channel();
    domainMapper.partialUpdate(request, channel);
    notifier.notifyUpdated(payloadMapper.toDto(channel, PayloadUpdatedMarker.class));
    return channelDetail;
  }

  @ServiceLogAround
  public void delete(UUID id) {
    Channel channel = findById(id);
    repository.delete(channel);
    notifier.notifyDeleted(payloadMapper.toDto(channel, PayloadDeletedMarker.class));
  }

  private ChannelDetailDto findByIdWithDetail(UUID id) {
    return DomainServiceSupport.getOrThrow(id, queryRepository::findChannelDetailById,
        value -> new ChannelException(ChannelErrorCode.CHANNELID_NOT_FOUND, value));
  }

  private Channel findById(UUID id) {
    return DomainServiceSupport.getOrThrow(id, repository::findById,
        value -> new ChannelException(ChannelErrorCode.CHANNELID_NOT_FOUND, value));
  }
}

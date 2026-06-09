package com.sprint.mission.discodeit.channel.service;

import com.sprint.mission.discodeit.channel.controller.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.channel.controller.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.channel.controller.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.channel.controller.dto.response.ChannelResponse;
import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.channel.domain.exception.ChannelErrorCode;
import com.sprint.mission.discodeit.channel.domain.exception.ChannelException;
import com.sprint.mission.discodeit.channel.controller.mapper.ChannelMapper;
import com.sprint.mission.discodeit.channel.infra.repository.ChannelRepository;
import com.sprint.mission.discodeit.channel.infra.repository.qdsl.dto.ChannelDetailDto;
import com.sprint.mission.discodeit.common.support.DomainServiceSupport;
import com.sprint.mission.discodeit.global.log.ServiceLogAround;
import com.sprint.mission.discodeit.readstatus.domain.entity.ReadStatus;
import com.sprint.mission.discodeit.readstatus.controller.mapper.ReadStatusMapper;
import com.sprint.mission.discodeit.readstatus.infra.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.user.domain.entity.User;
import com.sprint.mission.discodeit.user.infra.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChannelService implements DomainReferenceService<Channel> {

  private final UserRepository userRepository;
  private final ChannelRepository channelRepository;
  private final ReadStatusRepository readStatusRepository;
  private final ChannelMapper channelMapper;
  private final ReadStatusMapper readStatusMapper;

  @ServiceLogAround
  public ChannelResponse createPublic(PublicChannelCreateRequest request) {
    Channel channel = channelMapper.toEntityFrom(request);
    channelRepository.save(channel);
    return channelMapper.toDto(channel);
  }

  // todo: [warn] user id not found
  @ServiceLogAround
  public ChannelResponse createPrivate(PrivateChannelCreateRequest request) {
    Channel channel = channelMapper.toEntityFrom(request);
    channelRepository.save(channel);
    List<User> participants = userRepository.findProfileByIdIn(
        request.getParticipantIds());
    List<ReadStatus> readStatuses = readStatusMapper.toEntityFrom(channel, participants);
    readStatusRepository.saveAll(readStatuses);
    return channelMapper.toDtoFrom(channel, participants);
  }

  @ServiceLogAround
  @Transactional(readOnly = true)
  public ChannelResponse find(UUID id) {
    ChannelDetailDto channelDetail = findById(id);
    return channelMapper.toDto(channelDetail);
  }

  @ServiceLogAround
  @Transactional(readOnly = true)
  public List<ChannelResponse> findAllByUserId(UUID userId) {
    List<ChannelDetailDto> channelDetails = channelRepository.findVisibleChannelDetails(
        userId);
    return channelMapper.toDto(channelDetails);
  }

  @ServiceLogAround
  public ChannelResponse update(UUID id, PublicChannelUpdateRequest request) {
    ChannelDetailDto channelDetail = findById(id);
    channelMapper.partialUpdate(request, channelDetail.channel());
    return channelMapper.toDto(channelDetail);
  }

  @ServiceLogAround
  public void delete(UUID id) {
    DomainServiceSupport.executeOrThrow(id, channelRepository,
        value -> new ChannelException(ChannelErrorCode.CHANNELID_NOT_FOUND, value));
  }

  @Override
  public void existsOrThrow(UUID id) {
    DomainServiceSupport.requireOrThrow(id, channelRepository::existsById,
        value -> new ChannelException(ChannelErrorCode.CHANNELID_NOT_FOUND, value));
  }

  @Override
  public Channel getProxy(UUID id) {
    return channelRepository.getReferenceById(id);
  }

  private ChannelDetailDto findById(UUID id) {
    return DomainServiceSupport.getOrThrow(id, channelRepository::findChannelDetailById,
        value -> new ChannelException(ChannelErrorCode.CHANNELID_NOT_FOUND, value));
  }

}

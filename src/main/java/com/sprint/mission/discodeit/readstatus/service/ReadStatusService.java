package com.sprint.mission.discodeit.readstatus.service;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.common.support.DomainServiceSupport;
import com.sprint.mission.discodeit.readstatus.controller.dto.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.readstatus.controller.dto.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.readstatus.controller.dto.response.ReadStatusResponse;
import com.sprint.mission.discodeit.readstatus.controller.mapper.ReadStatusMapper;
import com.sprint.mission.discodeit.readstatus.domain.entity.ReadStatus;
import com.sprint.mission.discodeit.readstatus.domain.exception.ReadStatusErrorCode;
import com.sprint.mission.discodeit.readstatus.domain.exception.ReadStatusException;
import com.sprint.mission.discodeit.readstatus.domain.provider.ReadStatusChannelProvider;
import com.sprint.mission.discodeit.readstatus.domain.provider.ReadStatusUserProvider;
import com.sprint.mission.discodeit.readstatus.infra.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.user.domain.entity.User;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReadStatusService {

  private final ReadStatusRepository repository;
  private final ReadStatusMapper mapper;
  private final ReadStatusUserProvider userProvider;
  private final ReadStatusChannelProvider channelProvider;

  @Transactional(readOnly = true)
  public List<ReadStatusResponse> findAllByUserId(UUID userId) {
    return mapper.toDto(repository.findAllByUserId(userId));
  }

  public ReadStatusResponse create(ReadStatusCreateRequest request) {
    verifyCreatable(request);
    User user = userProvider.getProxyOrThrow(request.getUserId());
    Channel channel = channelProvider.getProxyOrThrow(request.getChannelId());
    ReadStatus status = ReadStatus.builder()
        .user(user)
        .channel(channel)
        .lastReadAt(request.getLastReadAt())
        .build();
    repository.save(status);
    return mapper.toDto(status);
  }

  @Transactional(readOnly = true)
  public ReadStatusResponse find(UUID id) {
    return mapper.toDto(findById(id));
  }

  public ReadStatusResponse update(UUID id, ReadStatusUpdateRequest request) {
    ReadStatus status = findById(id);
    mapper.partialUpdate(request, status);
    return mapper.toDto(status);
  }

  public void delete(UUID id) {
    DomainServiceSupport.executeOrThrow(id, repository,
        value -> new ReadStatusException(ReadStatusErrorCode.READSTATUSID_NOT_FOUND, value));
  }

  private ReadStatus findById(UUID id) {
    return DomainServiceSupport.getOrThrow(id, repository::findById,
        value -> new ReadStatusException(ReadStatusErrorCode.READSTATUSID_NOT_FOUND, value));
  }

  private void verifyCreatable(ReadStatusCreateRequest request) {
    UUID userId = request.getUserId();
    UUID channelId = request.getChannelId();
    DomainServiceSupport.requireOrThrow(Map.of("userId", userId, "channelId", channelId),
        map -> !repository.existsByUserIdAndChannelId(map.get("userId"),
            map.get("channelId")),
        map -> new ReadStatusException(ReadStatusErrorCode.READSTATUS_ALREADY_EXIST, map));
  }
}

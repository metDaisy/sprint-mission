package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.common.exception.code.ErrorCode;
import com.sprint.mission.discodeit.common.exception.custom.APIException;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusServiceDTO.CreatableDto;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusServiceDTO.ReadStatusCreateDto;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusServiceDTO.ReadStatusResponse;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusServiceDTO.ReadStatusUpdateDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.ReadStatusMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicReadStatusService extends BasicDomainService<ReadStatus> implements ReadStatusService {
    private final ReadStatusRepository readStatusRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final ReadStatusMapper readStatusMapper;

    @Override
    public List<ReadStatusResponse> findAllByUserId(UUID userId) {
        return readStatusRepository.findAllByUserId(userId)
                .stream()
                .map(readStatusMapper::toResponse)
                .toList();
    }

    @Override
    public ReadStatusResponse create(ReadStatusCreateDto dto) {
        verifyCreatable(dto);
        User user = userRepository.getReferenceById(dto.userId());
        Channel channel = channelRepository.getReferenceById(dto.channelId());
        ReadStatus status = new ReadStatus(user, channel, dto.lastReadAt());
        readStatusRepository.save(status);
        return readStatusMapper.toResponse(status);
    }

    @Override
    public ReadStatusResponse find(UUID id) {
        return readStatusMapper.toResponse(findById(id));
    }

    @Override
    public ReadStatusResponse update(ReadStatusUpdateDto dto) {
        ReadStatus status = findById(dto.id());
        status.update(dto);
        readStatusRepository.save(status);
        return readStatusMapper.toResponse(status);
    }

    @Override
    public void delete(UUID id) {
        deleteByIdOrThrow(id, readStatusRepository, new APIException(ErrorCode.READSTATUSID_NOT_FOUND, id));
    }

    @Override
    protected ReadStatus findById(UUID id) {
        return getOrThrow(id, readStatusRepository::findById,
                () -> new APIException(ErrorCode.READSTATUSID_NOT_FOUND, id));
    }

    @Override
    public ReadStatusResponse find(UUID userId, UUID channelId) {
        ReadStatus status = readStatusRepository.findByUserIdAndChannelId(userId, channelId)
                .orElseThrow(() -> new APIException(ErrorCode.READSTATUSID_NOT_FOUND, Map.of("userId", userId, "channelId", channelId)));
        return readStatusMapper.toResponse(status);
    }

    private void verifyCreatable(CreatableDto dto) {
        ensure(dto.userId(), userRepository::existsById, id -> new APIException(ErrorCode.USERID_NOT_FOUND, id));
        ensure(dto.channelId(), channelRepository::existsById, id -> new APIException(ErrorCode.CHANNELID_NOT_FOUND, id));
        ensure(dto, readStatusRepository::existsByUserIdAndChannelId, dto1 -> new APIException(ErrorCode.READSTATUS_ALREADY_EXIST, dto1));
    }
}

package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ReadStatusServiceDTO.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.ReadStatusServiceDTO.ReadStatusResponse;
import com.sprint.mission.discodeit.dto.ReadStatusServiceDTO.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicReadStatusService extends BasicDomainService<ReadStatus> implements ReadStatusService {
    private final ReadStatusRepository readStatusRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    @Override
    public List<ReadStatusResponse> findAllByUserId(UUID userId) {
        return readStatusRepository.filter(status -> status.matchUserId(userId))
                .map(ReadStatus::toResponse)
                .toList();
    }

    @Override
    public ReadStatusResponse create(ReadStatusCreateRequest model) {
        // todo: refactoring
        if (!userRepository.existsById(model.userId())) {
            throw new NoSuchElementException(ID_NOT_FOUND.formatted("User", model.userId()));
        }
        if (!channelRepository.existsById(model.channelId())) {
            throw new NoSuchElementException(ID_NOT_FOUND.formatted("Channel", model.channelId()));
        }
        if (readStatusRepository.existsByUserAndChannelId(model.userId(), model.channelId())) {
            throw new IllegalStateException(
                    "read status entity exist already containing (user id: %s, channel id: %s)".formatted(model.userId(), model.channelId()));
        }
        ReadStatus status = new ReadStatus(model.userId(), model.channelId());
        readStatusRepository.save(status);
        return status.toResponse();
    }

    @Override
    public ReadStatusResponse find(UUID id) {
        return findById(id).toResponse();
    }

    @Override
    public ReadStatusResponse update(ReadStatusUpdateRequest model) {
        ReadStatus status = findByChannelAndUser(model.channelId(), model.userId());
        status.update(model.type());
        readStatusRepository.save(status);
        return status.toResponse();
    }

    @Override
    public void delete(UUID id) {
        if (readStatusRepository.existsById(id)) {
            readStatusRepository.deleteById(id);
            return;
        }
        throw new NoSuchElementException(ID_NOT_FOUND.formatted("ReadStatus", id));
    }

    @Override
    protected ReadStatus findById(UUID id) {
        return findEntityById(id, "ReadStatus", readStatusRepository);
    }

    @Override
    public ReadStatusResponse find(UUID channelId, UUID userId) {
        return findByChannelAndUser(channelId, userId).toResponse();
    }

    private ReadStatus findByChannelAndUser(UUID channelId, UUID userId) {
        return readStatusRepository.filter(readStatus -> readStatus.matchChannelId(channelId))
                .filter(readStatus -> readStatus.matchUserId(userId))
                .findFirst()
                .get();
    }
}

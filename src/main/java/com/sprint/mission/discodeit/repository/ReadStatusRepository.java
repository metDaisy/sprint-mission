package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.dto.readstatus.ReadStatusServiceDTO.CreatableDto;
import com.sprint.mission.discodeit.entity.ReadStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadStatusRepository extends JpaRepository<ReadStatus, UUID> {
    boolean existsByUserIdAndChannelId(UUID userId, UUID channelId);

    default boolean existsByUserIdAndChannelId(CreatableDto dto) {
        return existsByUserIdAndChannelId(dto.userId(), dto.channelId());
    }

    Optional<ReadStatus> findByUserIdAndChannelId(UUID userId, UUID channelId);

    List<ReadStatus> findAllByChannelId(UUID channelId);

    List<ReadStatus> findAllByUserId(UUID userId);
}

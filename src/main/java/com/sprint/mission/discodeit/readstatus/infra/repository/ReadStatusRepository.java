package com.sprint.mission.discodeit.readstatus.infra.repository;

import com.sprint.mission.discodeit.readstatus.domain.entity.ReadStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadStatusRepository extends JpaRepository<ReadStatus, UUID> {

  boolean existsByUserIdAndChannelId(UUID userId, UUID channelId);

  List<ReadStatus> findAllByUserId(UUID userId);
}

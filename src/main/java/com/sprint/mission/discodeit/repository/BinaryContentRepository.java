package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.BinaryContentStatus;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BinaryContentRepository extends JpaRepository<BinaryContent, UUID> {
  @Modifying(clearAutomatically = true)
  @Query("update BinaryContent b set b.status = :status where b.id in :ids")
  void updateStatus(Collection<UUID> ids, BinaryContentStatus status);
}

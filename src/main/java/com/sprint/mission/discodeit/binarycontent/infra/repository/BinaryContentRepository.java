package com.sprint.mission.discodeit.binarycontent.infra.repository;

import com.sprint.mission.discodeit.binarycontent.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.binarycontent.domain.entity.constant.BinaryContentStatus;
import com.sprint.mission.discodeit.common.jpa.repository.DomainRepository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BinaryContentRepository extends DomainRepository<BinaryContent> {

  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("update BinaryContent b set b.status = :status where b.id in :ids")
  int updateStatus(Collection<UUID> ids, BinaryContentStatus status);

  @Query("select bc from BinaryContent bc where bc.id = :id and bc.status = 'SUCCESS'")
  Optional<BinaryContent> findSuccessById(UUID id);

  @Query("select bc from BinaryContent bc where bc.id in :ids and bc.status = 'SUCCESS'")
  List<BinaryContent> findAllSuccessByIds(Collection<UUID> ids);

  @Query("select count(b) > 0 from BinaryContent b where b.id = :id and b.status = 'SUCCESS'")
  boolean existsSuccessById(UUID id);
}

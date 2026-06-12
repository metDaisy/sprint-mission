package com.sprint.mission.discodeit.message.infra.repository;

import com.sprint.mission.discodeit.common.jpa.repository.DomainRepository;
import com.sprint.mission.discodeit.message.domain.entity.Message;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends DomainRepository<Message> {

  @EntityGraph(attributePaths = {"author", "author.status", "author.profile"})
  @Query("select m from Message m join fetch m.channel c where c.id = :channelId")
  Slice<Message> findSliceByChannelId(@Param("channelId") UUID channelId, Pageable pageable);

  @EntityGraph(attributePaths = {"author", "channel", "attachments", "author.profile"})
  Optional<Message> findWithFetchJoinById(UUID uuid);
}

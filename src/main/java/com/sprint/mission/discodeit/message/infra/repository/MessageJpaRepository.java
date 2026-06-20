package com.sprint.mission.discodeit.message.infra.repository;

import com.sprint.mission.discodeit.message.domain.entity.Message;
import com.sprint.mission.discodeit.message.domain.repository.MessageRepository;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageJpaRepository extends MessageRepository, JpaRepository<Message, UUID> {

  @Override
  @EntityGraph(attributePaths = {"author", "author.profile"})
  Slice<Message> findSliceByChannel_Id(UUID channelId, Pageable pageable);
}

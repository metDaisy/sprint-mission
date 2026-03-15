package com.sprint.mission.discodeit.repository.querydsl;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelQDSLRepository {
    Optional<Channel> findByIdWithLastMsgAt(UUID id);

    List<Channel> findAllWithLastMsgAt();

    List<Channel> findVisibleToWithLastMsgAt(UUID userId);
}

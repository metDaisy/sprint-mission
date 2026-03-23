package com.sprint.mission.repository.file;

import com.sprint.mission.entity.Channel;
import com.sprint.mission.entity.EntityType;
import com.sprint.mission.repository.ChannelRepository;

public class FileChannelRepository extends FileBaseRepository<Channel> implements ChannelRepository {
    public FileChannelRepository() {
        super(EntityType.CHANNEL);
    }
}

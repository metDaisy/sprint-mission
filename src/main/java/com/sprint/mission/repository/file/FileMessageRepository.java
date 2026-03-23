package com.sprint.mission.repository.file;

import com.sprint.mission.entity.EntityType;
import com.sprint.mission.entity.Message;
import com.sprint.mission.repository.MessageRepository;

public class FileMessageRepository extends FileBaseRepository<Message> implements MessageRepository {
    public FileMessageRepository() {
        super(EntityType.MESSAGE);
    }
}

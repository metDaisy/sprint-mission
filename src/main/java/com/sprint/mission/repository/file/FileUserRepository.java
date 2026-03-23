package com.sprint.mission.repository.file;

import com.sprint.mission.entity.EntityType;
import com.sprint.mission.entity.User;
import com.sprint.mission.repository.UserRepository;

public class FileUserRepository extends FileBaseRepository<User> implements UserRepository {
    public FileUserRepository() {
        super(EntityType.USER);
    }
}

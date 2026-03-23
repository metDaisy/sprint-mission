package com.sprint.mission.service.file;

import com.sprint.mission.entity.User;
import com.sprint.mission.service.UserService;

public class FileUserService extends FileBaseService<User> implements UserService {

    public FileUserService() {
        super("user");
    }

    @Override
    public User create(String userName) {
        User user = new User(userName);
        save(user);
        return user;
    }
}

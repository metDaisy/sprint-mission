package com.sprint.mission.service.jcf;

import com.sprint.mission.dto.UserServiceRequest.*;
import com.sprint.mission.entity.Channel;
import com.sprint.mission.entity.User;
import com.sprint.mission.service.UserService;

import java.util.Map;
import java.util.UUID;

public class JCFUserService extends JCFBaseService<User> implements UserService {
    private static final UserService instance = new JCFUserService();

    private JCFUserService() {
        super();
    }

    public static UserService getInstance() {
        return instance;
    }

    public JCFUserService(Map<UUID, User> data) {
        super(data);
    }

    @Override
    public User create(UserCreation model) {
        User user = new User(model.userName());
        data.put(user.getId(), user);
        return user;
    }

    @Override
    public void updateName(UserNameUpdate model) {
        doAction(model.userId(), user -> user.update(model.newUserName()));
    }

    public void registerChannel(UUID userId, Channel channel) {
        doAction(userId, user -> user.addChannel(channel));
    }
}

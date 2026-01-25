package com.sprint.mission.service;

import com.sprint.mission.dto.UserServiceRequest.*;
import com.sprint.mission.entity.Channel;
import com.sprint.mission.entity.User;

import java.util.UUID;

public interface UserService extends BaseService<User> {
    User create(UserCreation model);
    void updateName(UserNameUpdate model);
    void registerChannel(UUID userId, Channel channel);
}

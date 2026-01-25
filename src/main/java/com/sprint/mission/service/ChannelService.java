package com.sprint.mission.service;

import com.sprint.mission.dto.ChannelServiceRequest.*;
import com.sprint.mission.entity.Channel;
import com.sprint.mission.entity.User;

import java.util.UUID;

public interface ChannelService extends BaseService<Channel> {
    Channel create(ChannelCreation model);
    void updateName(ChannelNameUpdate model);
    void registerUser(UUID channelId, User user);
}

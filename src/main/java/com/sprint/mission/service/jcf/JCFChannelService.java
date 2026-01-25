package com.sprint.mission.service.jcf;

import com.sprint.mission.dto.ChannelServiceRequest.*;
import com.sprint.mission.entity.Channel;
import com.sprint.mission.entity.User;
import com.sprint.mission.service.ChannelService;

import java.util.UUID;

public class JCFChannelService extends JCFBaseService<Channel> implements ChannelService {
    private static final ChannelService instance = new JCFChannelService();

    private JCFChannelService() {
        super();
    }

    public static ChannelService getInstance() {
        return instance;
    }

    @Override
    public Channel create(ChannelCreation model) {
        Channel channel = new Channel(model.channelName());
        data.put(channel.getId(), channel);
        return channel;
    }

    @Override
    public void updateName(ChannelNameUpdate model) {
        doAction(model.channelId(), channel -> channel.update(model.newChannelName()));
    }

    @Override
    public void registerUser(UUID channelId, User user) {
        doAction(channelId, channel -> channel.addUser(user));
    }
}

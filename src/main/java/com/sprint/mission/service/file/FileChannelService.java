package com.sprint.mission.service.file;

import com.sprint.mission.entity.Channel;
import com.sprint.mission.service.ChannelService;

public class FileChannelService extends FileBaseService<Channel> implements ChannelService {

    public FileChannelService() {
        super("chn");
    }

    @Override
    public Channel create(String channelName) {
        Channel channel = new Channel(channelName);
        save(channel);
        return channel;
    }
}

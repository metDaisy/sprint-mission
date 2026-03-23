package com.sprint.mission.service.file;

import com.sprint.mission.entity.Channel;
import com.sprint.mission.entity.Message;
import com.sprint.mission.entity.User;
import com.sprint.mission.service.MessageService;

public class FileMessageService extends FileBaseService<Message> implements MessageService {

    public FileMessageService() {
        super("msg");
    }

    @Override
    public Message create(User user, Channel channel, String content) {
        Message message = new Message(user, channel, content);
        save(message);
        return message;
    }
}

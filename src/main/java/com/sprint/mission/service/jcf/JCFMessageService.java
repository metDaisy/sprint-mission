package com.sprint.mission.service.jcf;

import com.sprint.mission.dto.MessageServiceRequest;
import com.sprint.mission.entity.Channel;
import com.sprint.mission.entity.Message;
import com.sprint.mission.entity.User;
import com.sprint.mission.service.MessageService;

public class JCFMessageService extends JCFBaseService<Message> implements MessageService {
    private static final MessageService instance = new JCFMessageService();

    private JCFMessageService() {
        super();
    }

    public static MessageService getInstance() {
        return instance;
    }

    @Override
    public Message create(User user, Channel channel, String content) {
        Message message = new Message(user, channel, content);
        data.put(message.getId(), message);
        return message;
    }

    @Override
    public void updateContent(MessageServiceRequest.MessageUpdate model) {
        doAction(model.messageId(), message -> message.update(model.newContent()));
    }
}

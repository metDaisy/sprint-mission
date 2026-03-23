package com.sprint.mission.service;

import com.sprint.mission.dto.MessageServiceRequest;
import com.sprint.mission.entity.Channel;
import com.sprint.mission.entity.Message;
import com.sprint.mission.entity.User;

public interface MessageService extends BaseService<Message> {
    Message create(User user, Channel channel, String content);
    void updateContent(MessageServiceRequest.MessageUpdate model);
}

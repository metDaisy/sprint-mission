package com.sprint.mission.service.jcf;

import com.sprint.mission.service.ChannelService;
import com.sprint.mission.service.MessageService;
import com.sprint.mission.service.UserService;
import com.sprint.mission.service.validation.MessageServiceValidator;

public class JCFServiceFactory {
    private static final UserService userService = JCFUserService.getInstance();
    private static final ChannelService channelService = JCFChannelService.getInstance();
    private static final MessageService messageService
            = JCFMessageService.getInstance(
            new MessageServiceValidator(userService, channelService)
    );

    public static UserService getUserService() {
        return userService;
    }

    public static ChannelService getChannelService() {
        return channelService;
    }

    public static MessageService getMessageService() {
        return messageService;
    }
}

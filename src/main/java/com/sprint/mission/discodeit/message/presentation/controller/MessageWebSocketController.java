package com.sprint.mission.discodeit.message.presentation.controller;

import com.sprint.mission.discodeit.global.web.ws.stomp.constant.StompConstants;
import com.sprint.mission.discodeit.message.presentation.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.message.presentation.dto.response.MessageResponse;
import com.sprint.mission.discodeit.message.application.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
class MessageWebSocketController {

  private final MessageService service;

  @SendTo(StompConstants.SUB_MESSAGE_DESTINATION)
  @MessageMapping(StompConstants.PUB_MESSAGE_DESTINATION)
  public MessageResponse send(@Payload MessageCreateRequest request) {
    return service.create(request);
  }
}

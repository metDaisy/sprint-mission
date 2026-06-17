package com.sprint.mission.discodeit.global.web.ws.stomp.payload;

public record StompPayload(String destination, String type, Object data) {

}

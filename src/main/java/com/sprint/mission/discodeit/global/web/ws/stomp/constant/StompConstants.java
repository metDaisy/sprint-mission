package com.sprint.mission.discodeit.global.web.ws.stomp.constant;

import com.sprint.mission.discodeit.global.web.ws.constant.WebSocketConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StompConstants {

  public static final String PUB_MESSAGE_DESTINATION = WebSocketConstants.PUB_PREFIX + "/channels/{id}/messages";
  public static final String PUB_USER_DESTINATION = WebSocketConstants.PUB_PREFIX + "/users/{id}";
  public static final String PUB_NOTIFICATION_DESTINATION = WebSocketConstants.PUB_PREFIX + "/notifications/{id}";
  public static final String PUB_CHANNEL_DESTINATION = WebSocketConstants.PUB_PREFIX + "/channels";

  public static final String SUB_MESSAGE_DESTINATION = WebSocketConstants.SUB_PREFIX + "/channels/{id}";
  public static final String SUB_USER_DESTINATION = WebSocketConstants.SUB_PREFIX + "/users";
  public static final String SUB_NOTIFICATION_DESTINATION = WebSocketConstants.SUB_PREFIX + "/notifications/{id}";
  public static final String SUB_CHANNEL_DESTINATION = WebSocketConstants.SUB_PREFIX + "/channels";
}

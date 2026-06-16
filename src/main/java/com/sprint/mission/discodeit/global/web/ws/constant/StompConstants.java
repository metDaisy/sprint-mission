package com.sprint.mission.discodeit.global.web.ws.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StompConstants {

  public static final String TOPIC_PREFIX = "/topic";
  public static final String CHANNEL_DESTINATION = TOPIC_PREFIX + "/channels";
  public static final String USER_STATUS_DESTINATION = TOPIC_PREFIX + "/users";
  public static final String NOTIFICATION_DESTINATION = TOPIC_PREFIX + "/notifications";
}

package com.sprint.mission.discodeit.global.web.ws.util;

import com.sprint.mission.discodeit.global.web.ws.stomp.constant.StompConstants;
import com.sprint.mission.discodeit.global.web.ws.exception.StompErrorCode;
import com.sprint.mission.discodeit.global.web.ws.exception.StompException;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StompDestinationParser {

  public static UUID extractChannelId(String destination) {
    if (!StringUtils.hasText(destination) || !destination.startsWith(
        StompConstants.PUB_MESSAGE_DESTINATION)) {
      throw new StompException(StompErrorCode.DESTINATION_INCORRECT_FORMAT, "destination",
          destination);
    }
    String stringId = destination.replace(StompConstants.PUB_MESSAGE_DESTINATION, "")
        .substring(1);
    try {
      return UUID.fromString(stringId);
    } catch (IllegalArgumentException ex) {
      throw new StompException(StompErrorCode.INCORRECT_ID, "id", stringId);
    }
  }
}

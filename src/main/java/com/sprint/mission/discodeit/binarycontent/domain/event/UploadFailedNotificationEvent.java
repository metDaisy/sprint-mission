package com.sprint.mission.discodeit.binarycontent.domain.event;

import com.sprint.mission.discodeit.common.event.DomainEvent;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public final class UploadFailedNotificationEvent extends DomainEvent {

  private final String title;
  private final List<String> messages;

  public UploadFailedNotificationEvent(String title, List<String> messages) {
    this.title = title;
    this.messages = messages;
  }
}

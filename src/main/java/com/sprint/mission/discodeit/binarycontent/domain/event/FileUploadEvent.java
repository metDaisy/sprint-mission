package com.sprint.mission.discodeit.binarycontent.domain.event;

import com.sprint.mission.discodeit.common.event.DomainEvent;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public final class FileUploadEvent extends DomainEvent {

  private final Map<UUID, byte[]> data;

  public FileUploadEvent(Map<UUID, byte[]> data) {
    this.data = data;
  }
}

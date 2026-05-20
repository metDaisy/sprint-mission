package com.sprint.mission.discodeit.event.publisher;

import com.sprint.mission.discodeit.dto.FileUploadDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.base.BaseEntity;
import com.sprint.mission.discodeit.event.FileUploadEvent;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileUploadEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publishAllFileUploadEvent(List<BinaryContent> entities,
      List<FileUploadDto> files) {
    if (entities.isEmpty() || files.isEmpty()) {
      return;
    }
    applicationEventPublisher.publishEvent(new FileUploadEvent(zipIdWithBytes(entities, files)));
  }

  public void publishFileUploadEvent(BinaryContent entity, FileUploadDto file) {
    Map<UUID, byte[]> data = Map.of(entity.getId(), file.bytes());
    applicationEventPublisher.publishEvent(new FileUploadEvent(data));
  }

  private <T extends BaseEntity> Map<UUID, byte[]> zipIdWithBytes(List<T> entities,
      List<FileUploadDto> files) {
    return IntStream.range(0, entities.size())
        .boxed()
        .collect(Collectors.toMap(
            i -> entities.get(i).getId(),
            i -> files.get(i).bytes()));
  }
}

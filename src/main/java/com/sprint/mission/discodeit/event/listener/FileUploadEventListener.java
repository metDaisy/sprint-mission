package com.sprint.mission.discodeit.event.listener;

import com.sprint.mission.discodeit.event.FileUploadEvent;
import com.sprint.mission.discodeit.event.FileUploadResult;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileUploadEventListener {

  private final BinaryContentStorage binaryContentStorage;

  @Async("fileUploadExecutor")
  @TransactionalEventListener
  public void handleFileUpload(FileUploadEvent event) {
    List<FileUploadResult> results = binaryContentStorage.putAll(event.data());
    handleFailures(results, event.fallback());
  }

  private void handleFailures(List<FileUploadResult> results, Consumer<List<UUID>> fallback) {
    List<UUID> failedIds = new ArrayList<>();
    Map<String, Object> details = new HashMap<>();
    for (FileUploadResult result : results) {
      if (result.isFailed()) {
        UUID id = result.id();
        failedIds.add(id);
        details.put(id.toString(), result.errorMessage());
      }
    }
    if (failedIds.isEmpty()) {
      return;
    }
    log.error("[File_UPLOAD_PARTIAL_FAILURE] 일부 파일이 업로드 실패. 상세: {}", details);
    try {
      fallback.accept(failedIds);
    } catch (Exception e) {
      log.error("[CRITICAL] 문제가 된 id들 삭제 중 error 발생.", e);
    }
  }
}

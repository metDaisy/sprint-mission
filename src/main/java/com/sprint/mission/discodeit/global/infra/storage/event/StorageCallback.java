package com.sprint.mission.discodeit.global.infra.storage.event;

import com.sprint.mission.discodeit.common.storage.event.FileUploadResult;
import java.util.List;

public interface StorageCallback {

  void handleSuccess(List<FileUploadResult> results);

  void handleFailures(List<FileUploadResult> results);
}

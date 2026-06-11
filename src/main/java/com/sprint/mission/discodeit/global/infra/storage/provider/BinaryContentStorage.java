package com.sprint.mission.discodeit.global.infra.storage.provider;

import com.sprint.mission.discodeit.common.storage.event.FileUploadResult;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface BinaryContentStorage {

  UUID put(UUID id, byte[] bytes);

  List<FileUploadResult> putAll(Map<UUID, byte[]> files);

  String downloadUrl(UUID id);
}

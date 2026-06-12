package com.sprint.mission.discodeit.binarycontent.domain.provider;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface BinaryContentStorage {

  UUID put(UUID id, byte[] bytes);

  List<FileUploadResult> putAll(Map<UUID, byte[]> files);

  String downloadUrl(UUID id);
}

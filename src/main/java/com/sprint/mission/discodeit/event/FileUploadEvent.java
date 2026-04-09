package com.sprint.mission.discodeit.event;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public record FileUploadEvent(Map<UUID, byte[]> data,
                              Consumer<List<UUID>> fallback) {

}

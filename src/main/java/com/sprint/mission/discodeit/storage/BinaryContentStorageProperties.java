package com.sprint.mission.discodeit.storage;

import java.nio.file.Path;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "discodeit.storage.local")
public record BinaryContentStorageProperties(Path rootPath) {
}

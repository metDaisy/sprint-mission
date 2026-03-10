package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.common.exception.code.ErrorCode;
import com.sprint.mission.discodeit.common.exception.custom.APIException;
import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentServiceDTO.BinaryContentResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Component
@ConditionalOnProperty(
        prefix = "discodeit.storage",
        name = "type",
        havingValue = "local"
)
@EnableConfigurationProperties(LocalBCStorageProperties.class)
public class LocalBinaryContentStorage implements BinaryContentStorage {
    private final Path root;

    public LocalBinaryContentStorage(LocalBCStorageProperties properties) {
        this.root = properties.rootPath();
    }

    @Override
    public UUID put(UUID id, byte[] bytes) {
        Path path = resolvePath(id);
        if (isPresent(path)) {
            throw new APIException(ErrorCode.FILE_ALREADY_EXIST, path);
        }
        try {
            Files.write(path, bytes);
            return id;
        } catch (IOException e) {
            throw new APIException(ErrorCode.FILE_CANT_WRITE, e.getMessage());
        }
    }

    @Override
    public InputStream get(UUID id) {
        Path path = resolvePath(id);
        if (!isPresent(path)) {
            throw new APIException(ErrorCode.FILE_NOT_FOUND, path);
        }
        try {
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw new APIException(ErrorCode.FILE_CANT_READ, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Resource> download(BinaryContentResponse dto) {
        try (InputStream inputStream = get(dto.id())) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentLength(dto.size())
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(inputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new APIException(ErrorCode.ROOT_DIRECTORY_FAILED_TO_CREATE, root);
        }
    }

    private Path resolvePath(UUID id) {
        return root.resolve(id.toString());
    }

    private boolean isPresent(Path path) {
        return path.toFile().exists();
    }
}

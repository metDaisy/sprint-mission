package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.event.FileUploadResult;
import com.sprint.mission.discodeit.exception.DiscodeitException;
import com.sprint.mission.discodeit.exception.file.FileErrorCode;
import com.sprint.mission.discodeit.exception.file.FileException;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(BinaryContentStorageProperties.class)
public class LocalBinaryContentStorage implements BinaryContentStorage {

  private final Path root;
  private final Executor fileUploadWorker;

  public LocalBinaryContentStorage(BinaryContentStorageProperties properties,
      @Qualifier("fileUploadWorker") Executor fileUploadWorker) {
    this.root = properties.rootPath();
    this.fileUploadWorker = fileUploadWorker;
  }

  @Override
  public UUID put(UUID id, byte[] bytes) {
    Path path = resolvePath(id);
    throwOrNot(path, this::isPresent,
        value -> new FileException(FileErrorCode.FILE_ALREADY_EXIST, value));
    try {
      Files.write(path, bytes);
      return id;
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  @Override
  public List<FileUploadResult> putAll(Map<UUID, byte[]> files) {
    List<CompletableFuture<FileUploadResult>> futures = files.entrySet().stream()
        .map(this::putAsync)
        .toList();
    return futures.stream()
        .map(CompletableFuture::join)
        .toList();
  }

  @Override
  public InputStream get(UUID id) {
    Path path = resolvePath(id);
    throwOrNot(path, Predicate.not(this::isPresent),
        value -> new FileException(FileErrorCode.FILE_NOT_FOUND, value));
    try {
      return Files.newInputStream(path);
    } catch (IOException e) {
      throw new FileException(FileErrorCode.FILE_CANT_READ, path);
    }
  }

  @Override
  public ResponseEntity<Resource> download(BinaryContentDto dto) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentLength(dto.size())
        .contentType(MediaType.IMAGE_JPEG)
        .body(new InputStreamResource(get(dto.id())));
  }

  @PostConstruct
  public void init() {
    try {
      Files.createDirectories(root);
    } catch (IOException e) {
      throw new FileException(FileErrorCode.ROOT_DIRECTORY_FAILED_TO_CREATE, root);
    }
  }

  private Path resolvePath(UUID id) {
    return root.resolve(id.toString());
  }

  private boolean isPresent(Path path) {
    return path.toFile().exists();
  }

  private <T> void throwOrNot(T value, Predicate<T> condition,
      Function<T, DiscodeitException> exception) {
    if (condition.test(value)) {
      throw exception.apply(value);
    }
  }

  private CompletableFuture<FileUploadResult> putAsync(Entry<UUID, byte[]> entry) {
    return CompletableFuture.supplyAsync(() -> put(entry.getKey(), entry.getValue()),
        fileUploadWorker).handle(FileUploadResult::of);
  }
}

package com.sprint.mission.discodeit.global.infra.storage.provider;

import com.sprint.mission.discodeit.common.storage.event.FileUploadResult;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class AbstractBinaryContentStorage implements BinaryContentStorage {

  protected final Executor fileUploadWorker;
  protected final String internalPath;

  protected AbstractBinaryContentStorage(
      @Qualifier("fileUploadWorker") Executor fileUploadWorker,
      String internalPath) {
    this.fileUploadWorker = fileUploadWorker;
    this.internalPath = internalPath;
  }

  protected CompletableFuture<FileUploadResult> putAsync(Entry<UUID, byte[]> entry) {
    return CompletableFuture.supplyAsync(() -> this.put(entry.getKey(), entry.getValue()),
        fileUploadWorker).handle(FileUploadResult::of);
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

  protected String resolvePath(String path) {
    return String.join("/", internalPath, path);
  }
}

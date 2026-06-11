package com.sprint.mission.discodeit.global.infra.storage.provider;

import com.sprint.mission.discodeit.common.storage.event.FileUploadResult;
import com.sprint.mission.discodeit.global.log.ServiceLogAround;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractBinaryContentStorage implements BinaryContentStorage {

  protected final Executor fileUploadWorker;
  protected final String internalPath;

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

  @Override
  @ServiceLogAround
  public String downloadUrl(UUID id) {
    existsOrThrow(id);
    return resolveUrl(id);
  }

  protected abstract void existsOrThrow(UUID id);

  protected abstract String resolveUrl(UUID id);
}

package com.sprint.mission.discodeit.binarycontent.controller;

import com.sprint.mission.discodeit.binarycontent.controller.dto.request.FileUploadRequest;
import com.sprint.mission.discodeit.binarycontent.controller.dto.response.BinaryContentDto;
import com.sprint.mission.discodeit.binarycontent.service.BinaryContentService;
import com.sprint.mission.discodeit.global.infra.storage.constant.StorageHeaders;
import com.sprint.mission.discodeit.global.infra.storage.provider.BinaryContentStorage;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/binaryContents")
@RequiredArgsConstructor
class BinaryContentController {

  private final BinaryContentService binaryContentService;
  private final BinaryContentStorage binaryContentStorage;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<List<BinaryContentDto>> create(@RequestParam List<MultipartFile> files) {
    List<FileUploadRequest> request = files.stream().map(FileUploadRequest::from).toList();
    return ResponseEntity.status(HttpStatus.OK).body(binaryContentService.create(request));
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<BinaryContentDto> find(@PathVariable UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(binaryContentService.find(id));
  }

  @GetMapping
  public ResponseEntity<List<BinaryContentDto>> findMany(
      @RequestParam List<UUID> ids) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(binaryContentService.findAllByIdIn(ids));
  }

  @Profile("prod")
  @GetMapping(value = "/{id}/download")
  public ResponseEntity<?> download(@PathVariable UUID id) {
    binaryContentService.existsOrThrow(id);
    String url = binaryContentStorage.downloadUrl(id);
    return ResponseEntity.status(HttpStatus.OK)
        .header(StorageHeaders.X_ACCEL_REDIRECT, url)
        .build();
  }
}

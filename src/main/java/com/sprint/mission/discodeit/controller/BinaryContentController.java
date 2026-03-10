package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentServiceDTO.BinaryContentResponse;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/binaryContents")
@RequiredArgsConstructor
class BinaryContentController {
    private final BinaryContentService binaryContentService;
    private final BinaryContentStorage binaryContentStorage;

    @GetMapping(value = "/{binaryContentId}")
    public ResponseEntity<BinaryContentResponse> find(@PathVariable UUID binaryContentId) {
        return ResponseEntity.status(HttpStatus.OK).body(binaryContentService.find(binaryContentId));
    }

    @GetMapping
    public ResponseEntity<List<BinaryContentResponse>> findMany(@RequestParam List<UUID> binaryContentIds) {
        return ResponseEntity.status(HttpStatus.OK).body(binaryContentService.findAllByIdIn(binaryContentIds));
    }

    @GetMapping(value = "/{binaryContentId}/download")
    public ResponseEntity<?> download(@PathVariable UUID binaryContentId) {
        BinaryContentResponse dto = binaryContentService.find(binaryContentId);
        return binaryContentStorage.download(dto);
    }
}

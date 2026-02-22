package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.BinaryContentServiceDTO.BinaryContentResponse;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/binary-contents")
@RequiredArgsConstructor
class BinaryContentController {
    private final BinaryContentService binaryContentService;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity<BinaryContentResponse> find(@PathVariable UUID id) {
        return ResponseEntity.ok(binaryContentService.find(id));
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<BinaryContentResponse>> findMany(@RequestBody List<UUID> ids) {
        return ResponseEntity.ok(binaryContentService.findAllByIdIn(ids));
    }
}

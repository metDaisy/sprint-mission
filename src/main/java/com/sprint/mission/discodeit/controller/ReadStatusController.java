package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.common.util.TimeConverter;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusServiceDTO.ReadStatusDto;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusServiceDTO.ReadStatusResponse;
import com.sprint.mission.discodeit.dto.readstatus.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.readstatus.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.service.ReadStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/readStatuses")
@RequiredArgsConstructor
public class ReadStatusController {
    private final ReadStatusService readStatusService;

    @GetMapping
    public ResponseEntity<List<ReadStatusResponse>> find(@RequestParam UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(readStatusService.findAllByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<ReadStatusResponse> create(@RequestBody @Valid ReadStatusCreateRequest request) {
        ReadStatusDto dto = ReadStatusDto.builder()
                .userId(request.userId())
                .channelId(request.channelId())
                .lastReadAt(TimeConverter.toInstant(request.lastReadAt()))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(readStatusService.create(dto));
    }

    @PatchMapping(value = "/{readStatusId}")
    public ResponseEntity<ReadStatusResponse> update(@PathVariable UUID readStatusId,
                                                     @RequestBody @Valid ReadStatusUpdateRequest request) {
        ReadStatusDto dto = ReadStatusDto.builder()
                .id(readStatusId)
                .lastReadAt(TimeConverter.toInstant(request.newLastReadAt()))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(readStatusService.update(dto));
    }

}

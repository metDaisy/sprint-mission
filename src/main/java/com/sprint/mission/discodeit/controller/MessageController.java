package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.message.MessageServiceDTO.MessageDto;
import com.sprint.mission.discodeit.dto.message.MessageServiceDTO.MessageResponse;
import com.sprint.mission.discodeit.dto.message.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.message.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.service.MessageService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponse> create(@RequestPart @Valid MessageCreateRequest messageCreateRequest,
                                                  @RequestPart(required = false) @Nullable List<MultipartFile> attachments) {
        MessageDto dto = messageMapper.toDtoFromRequest(messageCreateRequest, Optional.ofNullable(attachments).orElse(List.of()));
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<MessageResponse>> findInChannel(@RequestParam UUID channelId) {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.findAllByChannelId(channelId));
    }

    @PatchMapping(value = "/{messageId}")
    public ResponseEntity<MessageResponse> update(@PathVariable UUID messageId,
                                                  @RequestBody @Valid MessageUpdateRequest request) {
        MessageDto dto = messageMapper.toDtoFromRequest(messageId, request);
        return ResponseEntity.status(HttpStatus.OK).body(messageService.update(dto));
    }

    @DeleteMapping(value = "/{messageId}")
    public ResponseEntity<?> delete(@PathVariable UUID messageId) {
        messageService.delete(messageId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

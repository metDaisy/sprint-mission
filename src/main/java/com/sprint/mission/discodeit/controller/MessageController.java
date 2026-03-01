package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.MessageServiceDTO.MessageCreateCommand;
import com.sprint.mission.discodeit.dto.MessageServiceDTO.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.MessageServiceDTO.MessageResponse;
import com.sprint.mission.discodeit.dto.MessageServiceDTO.MessageUpdateRequest;
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
import java.util.UUID;

@RestController
@RequestMapping(value = "/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponse> create(@RequestPart @Valid MessageCreateRequest messageCreateRequest,
                                                  @RequestPart(required = false) @Nullable List<MultipartFile> attachments) {
        if (attachments == null) {
            attachments = List.of();
        }
        MessageCreateCommand command = new MessageCreateCommand(messageCreateRequest, attachments);
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.create(command));
    }

    @RequestMapping(value = "/{messageId}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable UUID messageId,
                                    @RequestBody MessageUpdateRequest request) {
        messageService.update(request);
        return ResponseEntity.status(204).build();
    }

    @RequestMapping(value = "/{messageId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable UUID messageId) {
        messageService.delete(messageId);
        return ResponseEntity.status(204).build();
    }
}

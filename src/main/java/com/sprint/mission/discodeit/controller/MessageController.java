package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.MessageServiceDTO.MessageUpdateRequest;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(value = "/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @RequestMapping(value = "/{messageId}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable UUID messageId,
                                    @RequestBody MessageUpdateRequest request) throws IOException, ClassNotFoundException {
        messageService.update(request);
        return ResponseEntity.status(204).build();
    }

    @RequestMapping(value = "/{messageId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable UUID messageId) throws IOException, ClassNotFoundException {
        messageService.delete(messageId);
        return ResponseEntity.status(204).build();
    }
}

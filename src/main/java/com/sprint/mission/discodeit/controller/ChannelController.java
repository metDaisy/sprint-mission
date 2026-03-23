package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.ChannelServiceDTO.ChannelCreateRequest;
import com.sprint.mission.discodeit.dto.ChannelServiceDTO.ChannelResponse;
import com.sprint.mission.discodeit.dto.ChannelServiceDTO.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.dto.MessageServiceDTO.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.MessageServiceDTO.MessageResponse;
import com.sprint.mission.discodeit.dto.ReadStatusServiceDTO.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.ReadStatusServiceDTO.ReadStatusResponse;
import com.sprint.mission.discodeit.dto.ReadStatusServiceDTO.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.ReadType;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/channels")
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;
    private final MessageService messageService;
    private final ReadStatusService readStatusService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ChannelResponse> create(@RequestBody ChannelCreateRequest request) {
        return ResponseEntity.status(201).body(channelService.create(request));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ChannelResponse> find(@PathVariable UUID id) {
        return ResponseEntity.ok(channelService.find(id));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ChannelResponse>> findByUserId(@RequestParam UUID userId) {
        return ResponseEntity.ok(channelService.findAllByUserId(userId));
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<ChannelResponse> update(@RequestBody PublicChannelUpdateRequest request) {
        return ResponseEntity.ok(channelService.update(request));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        channelService.delete(id);
        return ResponseEntity.status(204).body("Channel is removed");
    }

    @RequestMapping(value = "/{channelId}/messages", method = RequestMethod.GET)
    public ResponseEntity<List<MessageResponse>> findMessagesByChannelId(@PathVariable UUID channelId) {
        return ResponseEntity.ok(messageService.findAllByChannelId(channelId));
    }

    @RequestMapping(value = "/{channelId}/messages", method = RequestMethod.POST)
    public ResponseEntity<MessageResponse> sendMessage(@PathVariable UUID channelId,
                                                       @RequestBody MessageCreateRequest request) {
        return ResponseEntity.ok(messageService.create(request));
    }

    @RequestMapping(value = "/{channelId}/users/{userId}/read-status", method = RequestMethod.POST)
    public ResponseEntity<ReadStatusResponse> createReadStatus(@PathVariable ReadStatusCreateRequest request) {
        return ResponseEntity.ok(readStatusService.create(request));
    }

    @RequestMapping(value = "/{channelId}/users/{userId}/read-status", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateReadStatus(@PathVariable UUID channelId,
                                              @PathVariable UUID userId,
                                              @RequestBody ReadType type) {
        readStatusService.update(new ReadStatusUpdateRequest(channelId, userId, type));
        return ResponseEntity.status(201).build();
    }

    @RequestMapping(value = "/{channelId}/users/{userId}/read-status")
    public ResponseEntity<ReadStatusResponse> findReadStatus(@PathVariable UUID channelId,
                                                             @PathVariable UUID userId) {
        return ResponseEntity.ok(readStatusService.find(channelId, userId));
    }
}

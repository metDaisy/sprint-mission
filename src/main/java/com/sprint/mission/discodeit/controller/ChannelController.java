package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.ChannelDto;
import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.ChannelResponse;
import com.sprint.mission.discodeit.dto.channel.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.channel.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.channel.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.service.ChannelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/channels")
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;

    @PostMapping(value = "/public")
    public ResponseEntity<ChannelResponse> create(@RequestBody @Valid PublicChannelCreateRequest request) {
        ChannelDto dto = ChannelDto.builder()
                .name(request.name())
                .description(request.description())
                .type(request.type())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(channelService.createPublic(dto));
    }

    @PostMapping(value = "/private")
    public ResponseEntity<ChannelResponse> create(@RequestBody @Valid PrivateChannelCreateRequest request) {
        ChannelDto dto = ChannelDto.builder()
                .participantIds(request.participantIds())
                .type(request.type())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(channelService.createPrivate(dto));
    }

    @GetMapping
    public ResponseEntity<List<ChannelResponse>> findByUserId(@RequestParam UUID userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(channelService.findAllByUserId(userId));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<ChannelResponse> update(
            @PathVariable UUID id,
            @RequestBody PublicChannelUpdateRequest request) {
        ChannelDto dto = ChannelDto.builder()
                .id(id)
                .name(request.name())
                .description(request.description())
                .type(request.type())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(channelService.update(dto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        channelService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

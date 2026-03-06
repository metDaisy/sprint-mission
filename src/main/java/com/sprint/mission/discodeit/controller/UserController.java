package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentServiceDTO.BinaryContentDto;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserDto;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserResponse;
import com.sprint.mission.discodeit.dto.user.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.user.request.UserUpdateRequest;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusServiceDTO.UserStatusDto;
import com.sprint.mission.discodeit.dto.userstatus.request.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.mapper.UserStatusMapper;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
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
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserStatusService userStatusService;
    private final UserMapper userMapper;
    private final UserStatusMapper statusMapper;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponse> find(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.find(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponse> create(
            @RequestPart @Valid UserCreateRequest userCreateRequest,
            @RequestPart @Nullable MultipartFile profile) {
        BinaryContentDto profileImageDto = getBinaryContentCreateDto(profile);
        UserDto userDto = userMapper.toDtoFromCreateRequest(userCreateRequest, profileImageDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userDto));
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponse> update(
            @PathVariable UUID id,
            @RequestPart @Valid UserUpdateRequest userUpdateRequest,
            @RequestPart @Nullable MultipartFile profile) {
        BinaryContentDto profileImageDto = getBinaryContentCreateDto(profile);
        UserDto userDto = userMapper.toDtoFromUpdateRequest(id, userUpdateRequest, profileImageDto);
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(value = "/{id}/userStatus")
    public ResponseEntity<UserStatusDto> updateUserStatus(
            @PathVariable UUID id,
            @RequestBody UserStatusUpdateRequest request) {
        UserStatusDto statusDto = statusMapper.toEntity(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(userStatusService.update(statusDto));
    }

    private BinaryContentDto getBinaryContentCreateDto(MultipartFile profile) {
        return Optional.ofNullable(profile)
                .map(UserMapper.profileImageMapper::toDtoFromFile)
                .orElse(null);
    }
}

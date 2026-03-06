package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentServiceDTO.BinaryContentDto;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserCreateDto;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserDto;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserResponse;
import com.sprint.mission.discodeit.dto.user.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.user.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(config = GlobalMapperConfig.class)
public interface UserMapper extends BaseMapper<UserDto, User> {
    BinaryContentMapper profileImageMapper = Mappers.getMapper(BinaryContentMapper.class);

    @Override
    @Mapping(source = "isOnline", target = "online")
    UserDto toDto(User entity);

    User toEntity(UserCreateDto createDto, BinaryContent profile, UserStatus status);

    UserResponse toResponse(User entity);

    UserDto toDtoFromCreateRequest(UserCreateRequest request, BinaryContentDto profile);

    UserDto toDtoFromUpdateRequest(UUID id, UserUpdateRequest request, BinaryContentDto profile);
}

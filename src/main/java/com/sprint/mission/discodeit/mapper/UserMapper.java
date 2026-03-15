package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentServiceDTO.BinaryContentResponse;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserDto;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserResponse;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(config = GlobalMapperConfig.class)
public interface UserMapper extends BaseMapper<UserDto, User, UserResponse> {
    BinaryContentMapper profileImageMapper = Mappers.getMapper(BinaryContentMapper.class);

    @Override
    @Mapping(source = "online", target = "online")
    UserDto toDto(User entity);

    @Override
    @Mapping(target = "online", source = "online")
    @Mapping(target = "profile", source = "user", qualifiedByName = "profileToResponse")
    UserResponse toResponse(User user);

    User toEntity(UserResponse response);

    @Named("profileToResponse")
    default BinaryContentResponse profileToResponse(User user) {
        return profileImageMapper.toResponse(user.getProfile());
    }
}

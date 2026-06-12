package com.sprint.mission.discodeit.auth.presentation.mapper;

import com.sprint.mission.discodeit.auth.presentation.dto.JwtLoginResponse;
import com.sprint.mission.discodeit.auth.domain.entity.RefreshToken;
import com.sprint.mission.discodeit.auth.domain.entity.UserCredential;
import com.sprint.mission.discodeit.common.mapper.config.GlobalMapperConfig;
import com.sprint.mission.discodeit.user.presentation.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GlobalMapperConfig.class)
public interface AuthMapper {

  @Mapping(target = ".", source = "user")
  UserResponse toUserResponse(UserCredential credential);

  @Mapping(target = "userDto", source = "token.user")
  JwtLoginResponse toDtoFrom(RefreshToken token, String accessToken, String refreshToken);
}

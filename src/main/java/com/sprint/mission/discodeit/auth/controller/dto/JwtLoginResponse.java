package com.sprint.mission.discodeit.auth.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sprint.mission.discodeit.user.dto.response.UserResponse;

public record JwtLoginResponse(UserResponse response,
                               String accessToken,
                               @JsonIgnore String refreshToken) {

}

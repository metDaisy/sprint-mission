package com.sprint.mission.discodeit.auth.dto;

import com.sprint.mission.discodeit.user.dto.response.UserResponse;

public record JwtLoginResponse(UserResponse response, String accessToken, String refreshToken) {

}

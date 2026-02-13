package com.sprint.mission.discodeit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.discodeit.dto.UserServiceDTO.UserCreateRequest;
import com.sprint.mission.discodeit.dto.UserServiceDTO.UserResponse;
import com.sprint.mission.discodeit.service.UserService;
import lombok.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    private final String PREFIX = "/v1/api";
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("")
    void find() {
    }

    @Test
    void findAll() {
    }

    @Test
    @DisplayName("유저 생성 시 201, 데이터 반환")
    void create1() throws Exception {
        UUID id = UUID.randomUUID();
        UserCreateRequest request = new UserCreateRequest("lee", "lee@gmail.com",
                "1234", null);
        UserResponse response = UserResponse.builder()
                .userId(id)
                .username(request.username())
                .email(request.email())
                .isActive(true)
                .profileId(null)
                .build();
        given(userService.create(any(UserCreateRequest.class))).willReturn(response);

        mockMvc.perform(post(getUri("/users"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(id.toString()))
                .andExpect(jsonPath("$.username").value(request.username()))
                .andExpect(jsonPath("$.email").value(request.email()))
                .andExpect(jsonPath("$.isActive").isBoolean())
                .andExpect(jsonPath("$.profileId").isEmpty());
    }

    @Test
    @DisplayName("유저 생성-1")
    void create2() {

    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void updateActiveStatus() {
    }

    private @NonNull String getUri(String uri) {
        return PREFIX + uri;
    }
}
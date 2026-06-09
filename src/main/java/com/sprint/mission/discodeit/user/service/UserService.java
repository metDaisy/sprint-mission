package com.sprint.mission.discodeit.user.service;

import com.sprint.mission.discodeit.binarycontent.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.common.support.DomainServiceSupport;
import com.sprint.mission.discodeit.global.log.ServiceLogAround;
import com.sprint.mission.discodeit.user.controller.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.user.controller.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.user.controller.dto.response.UserResponse;
import com.sprint.mission.discodeit.user.controller.mapper.UserMapper;
import com.sprint.mission.discodeit.user.domain.entity.User;
import com.sprint.mission.discodeit.user.domain.event.UserCreatedEvent;
import com.sprint.mission.discodeit.user.domain.event.UserUpdatedEvent;
import com.sprint.mission.discodeit.user.domain.exception.UserErrorCode;
import com.sprint.mission.discodeit.user.domain.exception.UserException;
import com.sprint.mission.discodeit.user.domain.provider.UserProfileProvider;
import com.sprint.mission.discodeit.user.infra.repository.UserRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;
  private final UserMapper mapper;
  private final UserProfileProvider userProfileProvider;
  private final ApplicationEventPublisher eventPublisher;

  @ServiceLogAround
  @Transactional(readOnly = true)
  public UserResponse find(UUID id) {
    User user = findById(id);
    return mapper.toDto(user);
  }

  @Transactional(readOnly = true)
  public List<UserResponse> findAll() {
    List<User> users = repository.findAllUsersProfileBy();
    return mapper.toDto(users);
  }

  @ServiceLogAround
  public UserResponse create(UserCreateRequest request) {
    checkEmailUniqueness(request.getEmail());
    checkUsernameUniqueness(request.getUsername());

    BinaryContent profile = userProfileProvider.getProxyOrThrow(request.getProfileId());
    User user = User.builder()
        .username(request.getUsername())
        .email(request.getEmail())
        .profile(profile)
        .role(request.getRole())
        .build();
    repository.save(user);
    eventPublisher.publishEvent(new UserCreatedEvent(user.getId(), request.getPassword()));
    return mapper.toDto(user);
  }

  @ServiceLogAround
  public UserResponse update(UUID id, UserUpdateRequest request) {
    User user = findById(id);
    if (user.updateEmail(request.getEmail())) {
      checkEmailUniqueness(request.getEmail());
    }
    if (user.updateUsername(request.getUsername())) {
      checkUsernameUniqueness(request.getUsername());
    }
    BinaryContent profile = userProfileProvider.getProxyOrThrow(request.getProfileId());
    user.updateProfile(profile);
    eventPublisher.publishEvent(new UserUpdatedEvent(id, request.getPassword()));
    return mapper.toDto(user);
  }

  @ServiceLogAround
  public void delete(UUID id) {
    DomainServiceSupport.executeOrThrow(id, repository,
        value -> new UserException(UserErrorCode.USERID_NOT_FOUND, value));
  }

  private User findById(UUID id) {
    return DomainServiceSupport.getOrThrow(id, repository::findProfileById,
        value -> new UserException(UserErrorCode.USERID_NOT_FOUND, value));
  }

  private void checkUsernameUniqueness(String username) {
    DomainServiceSupport.requireOrThrow(username, Predicate.not(repository::existsByUsername),
        value -> new UserException(UserErrorCode.USERNAME_ALREADY_EXIST,
            Map.of("username", value)));
  }

  private void checkEmailUniqueness(String email) {
    DomainServiceSupport.requireOrThrow(email, Predicate.not(repository::existsByEmail),
        value -> new UserException(UserErrorCode.EMAIL_ALREADY_EXIST,
            Map.of("email", value)));
  }
}

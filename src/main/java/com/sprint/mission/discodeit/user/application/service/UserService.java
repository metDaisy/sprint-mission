package com.sprint.mission.discodeit.user.application.service;

import com.sprint.mission.discodeit.binarycontent.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.common.payload.marker.PayloadCreatedMarker;
import com.sprint.mission.discodeit.common.payload.marker.PayloadDeletedMarker;
import com.sprint.mission.discodeit.common.payload.marker.PayloadUpdatedMarker;
import com.sprint.mission.discodeit.common.support.DomainServiceSupport;
import com.sprint.mission.discodeit.global.log.ServiceLogAround;
import com.sprint.mission.discodeit.user.application.mapper.UserPayloadMapper;
import com.sprint.mission.discodeit.user.domain.entity.User;
import com.sprint.mission.discodeit.user.domain.entity.constant.UserRole;
import com.sprint.mission.discodeit.user.domain.event.UserCreatedEvent;
import com.sprint.mission.discodeit.user.domain.event.UserDeletedEvent;
import com.sprint.mission.discodeit.user.domain.event.UserRoleUpdateEvent;
import com.sprint.mission.discodeit.user.domain.event.UserUpdatedEvent;
import com.sprint.mission.discodeit.user.domain.exception.UserErrorCode;
import com.sprint.mission.discodeit.user.domain.exception.UserException;
import com.sprint.mission.discodeit.user.domain.provider.UserNotifier;
import com.sprint.mission.discodeit.user.domain.provider.UserProfileResolver;
import com.sprint.mission.discodeit.user.domain.repository.UserRepository;
import com.sprint.mission.discodeit.user.presentation.dto.request.RoleUpdateRequest;
import com.sprint.mission.discodeit.user.presentation.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.user.presentation.dto.request.UserUpdateRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;
  private final UserProfileResolver profileProvider;
  private final ApplicationEventPublisher eventPublisher;
  private final UserNotifier notifier;
  private final UserPayloadMapper payloadMapper;

  @ServiceLogAround
  @Transactional(readOnly = true)
  public User find(UUID id) {
    return findById(id);
  }

  @Transactional(readOnly = true)
  public List<User> findAll() {
    return repository.findAllUsersProfileBy();
  }

  @ServiceLogAround
  public User create(UserCreateRequest request) {
    checkEmailUniqueness(request.getEmail());
    checkUsernameUniqueness(request.getUsername());

    User user = User.builder()
        .username(request.getUsername())
        .email(request.getEmail())
        .role(UserRole.USER)
        .build();
    repository.save(user);
    eventPublisher.publishEvent(new UserCreatedEvent(user.getId(), request.getPassword()));
    notifier.notifyCreated(payloadMapper.toDto(user, PayloadCreatedMarker.class));
    return user;
  }

  @ServiceLogAround
  public User update(UUID id, UserUpdateRequest request) {
    User user = findById(id);
    user.updateEmail(request.getEmail(), this::checkEmailUniqueness);
    user.updateUsername(request.getUsername(), this::checkUsernameUniqueness);
    if (request.getProfileId() != null) {
      BinaryContent profile = profileProvider.getOrThrow(request.getProfileId());
      user.updateProfile(profile);
    }
    if (StringUtils.hasText(request.getPassword())) {
      eventPublisher.publishEvent(new UserUpdatedEvent(id, request.getPassword()));
    }
    notifier.notifyUpdated(payloadMapper.toDto(user, PayloadUpdatedMarker.class));
    return user;
  }

  @ServiceLogAround
  public void delete(UUID id) {
    User user = findById(id);
    eventPublisher.publishEvent(new UserDeletedEvent(id));
    repository.delete(user);
    notifier.notifyDeleted(payloadMapper.toDto(user, PayloadDeletedMarker.class));
  }

  @ServiceLogAround
  public User updateRole(RoleUpdateRequest request) {
    UUID id = request.getId();
    User user = findById(id);
    UserRole oldRole = user.getRole();
    UserRole newRole = request.getRole();
    user.updateRole(newRole);
    eventPublisher.publishEvent(new UserRoleUpdateEvent(id, oldRole, newRole));
    return user;
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

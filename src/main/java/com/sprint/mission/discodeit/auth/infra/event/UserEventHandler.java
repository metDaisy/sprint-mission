package com.sprint.mission.discodeit.auth.infra.event;

import com.sprint.mission.discodeit.auth.domain.entity.UserCredential;
import com.sprint.mission.discodeit.auth.domain.exception.AuthException;
import com.sprint.mission.discodeit.auth.domain.exception.UserCredentialErrorCode;
import com.sprint.mission.discodeit.auth.infra.repository.UserCredentialRepository;
import com.sprint.mission.discodeit.common.support.DomainServiceSupport;
import com.sprint.mission.discodeit.user.domain.entity.User;
import com.sprint.mission.discodeit.user.domain.event.UserCreatedEvent;
import com.sprint.mission.discodeit.user.domain.event.UserUpdatedEvent;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventHandler {

  private final UserCredentialRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final EntityManager em;

  @EventListener
  public void handleUserCreatedEvent(UserCreatedEvent event) {
    UserCredential credential = UserCredential.builder()
        .user(em.getReference(User.class, event.id()))
        .password(passwordEncoder.encode(event.password()))
        .build();
    repository.save(credential);
  }

  @EventListener
  public void handleUserUpdatedEvent(UserUpdatedEvent event) {
    UserCredential credential = DomainServiceSupport.getOrThrow(event.id(),
        repository::findByUser_Id,
        value -> new AuthException(UserCredentialErrorCode.USER_CREDENTIAL_NOT_FOUND));
    credential.updatePassword(passwordEncoder.encode(event.password()));
  }
}

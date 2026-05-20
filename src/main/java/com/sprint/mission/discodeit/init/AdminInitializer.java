package com.sprint.mission.discodeit.init;

import com.sprint.mission.discodeit.auth.constant.DiscodeitRole;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserCredential;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserCredentialRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AdminInitializer {

  private final UserRepository userRepository;
  private final UserCredentialRepository userCredentialRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  @EventListener(ApplicationReadyEvent.class)
  public void init() {
    boolean hasAdmin = userRepository.existsByRole(DiscodeitRole.ADMIN);
    if (hasAdmin) {
      return;
    }
    User admin = User.builder()
        .username("admin")
        .email("admin@admin.com")
        .role(DiscodeitRole.ADMIN)
        .status(UserStatus.builder().lastActiveAt(Instant.now()).build())
        .build();
    userRepository.save(admin);
    UserCredential userCredential = new UserCredential(admin, passwordEncoder.encode("admin"));
    userCredentialRepository.save(userCredential);
  }
}

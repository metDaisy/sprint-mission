package com.sprint.mission.discodeit.init;

import com.sprint.mission.discodeit.auth.constant.DiscodeitRole;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserCredential;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserCredentialRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
  @Value("${discodeit.admin.username}")
  private String adminUsername;
  @Value("${discodeit.admin.email}")
  private String adminEmail;
  @Value("${discodeit.admin.password}")
  private String adminPassword;

  @Transactional
  @EventListener(ApplicationReadyEvent.class)
  public void init() {
    boolean hasAdmin = userRepository.existsByRole(DiscodeitRole.ADMIN);
    if (hasAdmin) {
      return;
    }
    UserStatus status = UserStatus.builder().lastActiveAt(Instant.now()).build();
    User admin = User.builder()
        .username(adminUsername)
        .email(adminEmail)
        .role(DiscodeitRole.ADMIN)
        .status(status)
        .build();
    status.setUser(admin);
    userRepository.save(admin);
    UserCredential userCredential = new UserCredential(admin,
        passwordEncoder.encode(adminPassword));
    userCredentialRepository.save(userCredential);
  }
}

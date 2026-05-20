package com.sprint.mission.discodeit.auth;

import com.sprint.mission.discodeit.entity.UserCredential;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.UserCredentialRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DiscodeitUserDetailsService implements UserDetailsService {

  private final UserCredentialRepository userCredentialRepository;
  private final UserMapper userMapper;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String stringUserId) throws UsernameNotFoundException {
    UUID userId = toUUID(stringUserId);
    UserCredential credential = userCredentialRepository.findByUser_Id(userId)
        .orElseThrow(() -> new UsernameNotFoundException("not found user credentials"));
    return new DiscodeitUserDetails(userMapper.toDto(credential.getUser()),
        credential.getPassword());
  }

  private UUID toUUID(String userId) {
    try {
      return UUID.fromString(userId);
    } catch (IllegalArgumentException e) {
      throw new UsernameNotFoundException("wrong format userId, %s".formatted(userId));
    }
  }
}

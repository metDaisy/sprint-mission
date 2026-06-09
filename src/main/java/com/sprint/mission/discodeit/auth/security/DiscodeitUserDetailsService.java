package com.sprint.mission.discodeit.auth.security;

import com.sprint.mission.discodeit.auth.controller.mapper.AuthMapper;
import com.sprint.mission.discodeit.auth.domain.entity.UserCredential;
import com.sprint.mission.discodeit.auth.domain.exception.AuthException;
import com.sprint.mission.discodeit.auth.domain.exception.UserCredentialErrorCode;
import com.sprint.mission.discodeit.auth.infra.repository.UserCredentialRepository;
import com.sprint.mission.discodeit.common.support.DomainServiceSupport;
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

  private final UserCredentialRepository repository;
  private final AuthMapper mapper;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserCredential credential = DomainServiceSupport.getOrThrow(username,
        repository::findByUser_Email,
        value -> new AuthException(UserCredentialErrorCode.USERNAME_NOT_FOUND, "email", value));
    return new DiscodeitUserDetails(mapper.toUserResponse(credential.getUser()),
        credential.getPassword());
  }

}

package com.sprint.mission.discodeit.auth;

import com.sprint.mission.discodeit.auth.constant.DiscodeitRole;
import com.sprint.mission.discodeit.auth.utils.DiscodeitAuthorityUtils;
import java.util.Collection;
import java.util.UUID;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class DiscodeitUserDetails implements UserDetails {

  private final UUID userId;
  private final String password;
  private final Collection<GrantedAuthority> authorities;

  public DiscodeitUserDetails(UUID userId, String password, Collection<DiscodeitRole> authorities) {
    this.userId = userId;
    this.password = password;
    this.authorities = DiscodeitAuthorityUtils.toGrantedAuthorities(authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return userId.toString();
  }
}

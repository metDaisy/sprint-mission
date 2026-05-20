package com.sprint.mission.discodeit.auth;

import com.sprint.mission.discodeit.auth.utils.DiscodeitAuthorityUtils;
import com.sprint.mission.discodeit.dto.UserDto;
import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class DiscodeitUserDetails implements UserDetails {

  @Getter
  private final UserDto userDto;
  private final String password;
  private final Collection<GrantedAuthority> authorities;

  public DiscodeitUserDetails(UserDto userDto, String password) {
    this.userDto = userDto;
    this.password = password;
    this.authorities = DiscodeitAuthorityUtils.toGrantedAuthorities(userDto.role());
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
    return userDto.id().toString();
  }
}

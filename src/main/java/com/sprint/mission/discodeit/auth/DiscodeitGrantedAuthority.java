package com.sprint.mission.discodeit.auth;

import com.sprint.mission.discodeit.auth.constant.DiscodeitRole;
import java.io.Serial;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

@RequiredArgsConstructor
public final class DiscodeitGrantedAuthority implements GrantedAuthority {

  @Serial
  private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

  private final DiscodeitRole role;

  @Override
  public String getAuthority() {
    return role.name();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj instanceof DiscodeitGrantedAuthority that) {
      return getAuthority().equals(that.getAuthority());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return role.hashCode();
  }
}

package com.sprint.mission.discodeit.auth.utils;

import com.sprint.mission.discodeit.auth.DiscodeitGrantedAuthority;
import com.sprint.mission.discodeit.auth.constant.DiscodeitRole;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;

public final class DiscodeitAuthorityUtils {

  public static Set<GrantedAuthority> toGrantedAuthorities(DiscodeitRole role) {
    return Set.of(new DiscodeitGrantedAuthority(role));
  }
}

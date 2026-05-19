package com.sprint.mission.discodeit.auth.utils;

import com.sprint.mission.discodeit.auth.DiscodeitGrantedAuthority;
import com.sprint.mission.discodeit.auth.constant.DiscodeitRole;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;

public final class DiscodeitAuthorityUtils {

  public static Set<GrantedAuthority> toGrantedAuthorities(Collection<DiscodeitRole> roles) {
    return roles.stream().map(DiscodeitGrantedAuthority::new)
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }
}

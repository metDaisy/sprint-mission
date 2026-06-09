package com.sprint.mission.discodeit.auth.infra;

import com.sprint.mission.discodeit.auth.domain.provider.AuthUserProvider;
import com.sprint.mission.discodeit.common.provider.AbstractServiceReferenceProvider;
import com.sprint.mission.discodeit.common.service.DomainReferenceService;
import com.sprint.mission.discodeit.user.domain.entity.User;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class AuthUserProviderImpl
    extends AbstractServiceReferenceProvider<User>
    implements AuthUserProvider {

  public AuthUserProviderImpl(DomainReferenceService<User> service) {
    super(service);
  }

  @Override
  public User getProxy(UUID id) {
    return service.getProxy(id);
  }
}

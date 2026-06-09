package com.sprint.mission.discodeit.readstatus.infra;

import com.sprint.mission.discodeit.common.provider.AbstractServiceReferenceProvider;
import com.sprint.mission.discodeit.common.service.DomainReferenceService;
import com.sprint.mission.discodeit.readstatus.domain.provider.ReadStatusUserProvider;
import com.sprint.mission.discodeit.user.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ReadStatusUserProviderImpl
    extends AbstractServiceReferenceProvider<User>
    implements ReadStatusUserProvider {

  public ReadStatusUserProviderImpl(
      DomainReferenceService<User> service) {
    super(service);
  }
}

package com.sprint.mission.discodeit.readstatus.infra;

import com.sprint.mission.discodeit.common.provider.AbstractEntityReferenceProvider;
import com.sprint.mission.discodeit.common.service.DomainReferenceService;
import com.sprint.mission.discodeit.readstatus.domain.provider.ReadStatusUserProvider;
import com.sprint.mission.discodeit.user.domain.entity.User;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ReadStatusUserProviderImpl
    extends AbstractEntityReferenceProvider<User>
    implements ReadStatusUserProvider {

  public ReadStatusUserProviderImpl(
      DomainReferenceService<User> service) {
    super(service);
  }

  @Override
  public List<User> getProxy(List<UUID> ids) {
    return service.getProxy(ids);
  }
}

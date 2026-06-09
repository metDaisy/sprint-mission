package com.sprint.mission.discodeit.channel.infra;

import com.sprint.mission.discodeit.channel.domain.provider.ChannelUserProvider;
import com.sprint.mission.discodeit.common.provider.AbstractServiceReferenceProvider;
import com.sprint.mission.discodeit.common.service.DomainReferenceService;
import com.sprint.mission.discodeit.user.domain.entity.User;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ChannelUserProviderImpl
    extends AbstractServiceReferenceProvider<User>
    implements ChannelUserProvider {

  public ChannelUserProviderImpl(
      DomainReferenceService<User> service) {
    super(service);
  }

  @Override
  public List<User> getOrThrow(Collection<UUID> ids) {
    return service.getOrThrow(ids);
  }
}

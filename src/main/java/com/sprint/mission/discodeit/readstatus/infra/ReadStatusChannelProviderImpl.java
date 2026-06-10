package com.sprint.mission.discodeit.readstatus.infra;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.common.provider.AbstractEntityReferenceProvider;
import com.sprint.mission.discodeit.common.service.DomainReferenceService;
import com.sprint.mission.discodeit.readstatus.domain.provider.ReadStatusChannelProvider;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ReadStatusChannelProviderImpl
    extends AbstractEntityReferenceProvider<Channel>
    implements ReadStatusChannelProvider {

  public ReadStatusChannelProviderImpl(
      DomainReferenceService<Channel> service) {
    super(service);
  }

  @Override
  public Channel getProxy(UUID id) {
    return service.getProxy(id);
  }
}

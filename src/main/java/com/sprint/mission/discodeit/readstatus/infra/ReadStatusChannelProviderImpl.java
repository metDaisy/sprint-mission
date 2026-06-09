package com.sprint.mission.discodeit.readstatus.infra;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.common.provider.AbstractServiceReferenceProvider;
import com.sprint.mission.discodeit.common.service.DomainReferenceService;
import com.sprint.mission.discodeit.readstatus.domain.provider.ReadStatusChannelProvider;
import org.springframework.stereotype.Component;

@Component
public class ReadStatusChannelProviderImpl
    extends AbstractServiceReferenceProvider<Channel>
    implements ReadStatusChannelProvider {

  public ReadStatusChannelProviderImpl(
      DomainReferenceService<Channel> service) {
    super(service);
  }
}

package com.sprint.mission.discodeit.message.infra;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.common.provider.AbstractServiceReferenceProvider;
import com.sprint.mission.discodeit.common.service.DomainReferenceService;
import com.sprint.mission.discodeit.message.domain.provider.MessageChannelProvider;
import org.springframework.stereotype.Component;

@Component
public class MessageChannelProviderImpl
    extends AbstractServiceReferenceProvider<Channel>
    implements MessageChannelProvider {

  public MessageChannelProviderImpl(
      DomainReferenceService<Channel> service) {
    super(service);
  }
}

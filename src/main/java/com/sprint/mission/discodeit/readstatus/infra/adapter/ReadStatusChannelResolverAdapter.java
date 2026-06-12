package com.sprint.mission.discodeit.readstatus.infra.adapter;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.common.reference.resolver.AbstractEntityReferenceResolver;
import com.sprint.mission.discodeit.common.reference.supplier.EntityReferenceSupplier;
import com.sprint.mission.discodeit.readstatus.domain.provider.ReadStatusChannelResolver;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ReadStatusChannelResolverAdapter
    extends AbstractEntityReferenceResolver<Channel>
    implements ReadStatusChannelResolver {

  public ReadStatusChannelResolverAdapter(
      EntityReferenceSupplier<Channel> service) {
    super(service);
  }

  @Override
  public Channel getProxy(UUID id) {
    return service.getProxy(id);
  }
}

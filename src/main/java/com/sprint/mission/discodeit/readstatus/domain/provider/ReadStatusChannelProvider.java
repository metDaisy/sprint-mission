package com.sprint.mission.discodeit.readstatus.domain.provider;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.common.provider.EntityReferenceProvider;
import java.util.UUID;

public interface ReadStatusChannelProvider extends EntityReferenceProvider<Channel> {

  Channel getProxy(UUID id);
}

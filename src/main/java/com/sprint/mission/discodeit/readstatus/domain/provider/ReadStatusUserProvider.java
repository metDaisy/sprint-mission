package com.sprint.mission.discodeit.readstatus.domain.provider;

import com.sprint.mission.discodeit.common.provider.EntityReferenceProvider;
import com.sprint.mission.discodeit.user.domain.entity.User;
import java.util.List;
import java.util.UUID;

public interface ReadStatusUserProvider extends EntityReferenceProvider<User> {

  List<User> getProxy(List<UUID> ids);
}

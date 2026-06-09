package com.sprint.mission.discodeit.auth.domain.provider;

import com.sprint.mission.discodeit.common.provider.EntityReferenceProvider;
import com.sprint.mission.discodeit.user.domain.entity.User;
import java.util.UUID;

public interface AuthUserProvider extends EntityReferenceProvider<User> {

  User getProxy(UUID id);
}

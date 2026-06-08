package com.sprint.mission.discodeit.user.domain.provider;

import com.sprint.mission.discodeit.binarycontent.entity.BinaryContent;
import java.util.UUID;

public interface UserProfileProvider {

  BinaryContent getProxyOrNot(UUID id);
}

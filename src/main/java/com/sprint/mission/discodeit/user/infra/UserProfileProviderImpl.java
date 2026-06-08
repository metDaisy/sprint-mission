package com.sprint.mission.discodeit.user.infra;

import com.sprint.mission.discodeit.binarycontent.entity.BinaryContent;
import com.sprint.mission.discodeit.binarycontent.service.BinaryContentService;
import com.sprint.mission.discodeit.user.domain.provider.UserProfileProvider;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProfileProviderImpl implements UserProfileProvider {

  private final BinaryContentService service;

  @Override
  public BinaryContent getProxyOrNot(UUID id) {
    if (service.exists(id)) {
      return service.getProxy(id);
    }
    return null;
  }
}

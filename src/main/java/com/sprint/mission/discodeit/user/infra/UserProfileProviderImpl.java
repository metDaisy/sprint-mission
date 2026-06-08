package com.sprint.mission.discodeit.user.infra;

import com.sprint.mission.discodeit.binarycontent.entity.BinaryContent;
import com.sprint.mission.discodeit.common.provider.AbstractServiceReferenceProvider;
import com.sprint.mission.discodeit.common.service.DomainReferenceService;
import com.sprint.mission.discodeit.user.domain.provider.UserProfileProvider;
import org.springframework.stereotype.Component;

@Component
public class UserProfileProviderImpl
    extends AbstractServiceReferenceProvider<BinaryContent>
    implements UserProfileProvider {

  public UserProfileProviderImpl(DomainReferenceService<BinaryContent> service) {
    super(service);
  }
}

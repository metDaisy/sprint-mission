package com.sprint.mission.discodeit.message.infra;

import com.sprint.mission.discodeit.common.provider.AbstractEntityReferenceProvider;
import com.sprint.mission.discodeit.common.service.DomainReferenceService;
import com.sprint.mission.discodeit.message.domain.provider.MessageUserProvider;
import com.sprint.mission.discodeit.user.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class MessageUserProviderImpl
    extends AbstractEntityReferenceProvider<User>
    implements MessageUserProvider {

  public MessageUserProviderImpl(
      DomainReferenceService<User> service) {
    super(service);
  }
}

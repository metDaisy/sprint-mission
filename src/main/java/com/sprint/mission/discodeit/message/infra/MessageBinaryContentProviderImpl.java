package com.sprint.mission.discodeit.message.infra;

import com.sprint.mission.discodeit.binarycontent.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.common.provider.AbstractEntityReferenceProvider;
import com.sprint.mission.discodeit.common.service.DomainReferenceService;
import com.sprint.mission.discodeit.message.domain.provider.MessageBinaryContentProvider;
import org.springframework.stereotype.Component;

@Component
public class MessageBinaryContentProviderImpl
    extends AbstractEntityReferenceProvider<BinaryContent>
    implements MessageBinaryContentProvider {

  public MessageBinaryContentProviderImpl(
      DomainReferenceService<BinaryContent> service) {
    super(service);
  }
}

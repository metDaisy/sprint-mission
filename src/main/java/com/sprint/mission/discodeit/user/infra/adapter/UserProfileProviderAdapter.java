package com.sprint.mission.discodeit.user.infra.adapter;

import com.sprint.mission.discodeit.binarycontent.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.common.reference.resolver.AbstractEntityReferenceResolver;
import com.sprint.mission.discodeit.common.reference.supplier.EntityReferenceSupplier;
import com.sprint.mission.discodeit.user.domain.provider.UserProfileProvider;
import org.springframework.stereotype.Component;

@Component
public class UserProfileProviderAdapter
    extends AbstractEntityReferenceResolver<BinaryContent>
    implements UserProfileProvider {

  public UserProfileProviderAdapter(EntityReferenceSupplier<BinaryContent> service) {
    super(service);
  }
}

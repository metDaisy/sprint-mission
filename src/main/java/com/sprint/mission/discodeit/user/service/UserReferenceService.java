package com.sprint.mission.discodeit.user.service;

import com.sprint.mission.discodeit.common.exception.DiscodeitException;
import com.sprint.mission.discodeit.common.jpa.repository.DomainRepository;
import com.sprint.mission.discodeit.common.service.AbstractDomainReferenceService;
import com.sprint.mission.discodeit.user.domain.entity.User;
import com.sprint.mission.discodeit.user.domain.exception.UserErrorCode;
import com.sprint.mission.discodeit.user.domain.exception.UserException;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserReferenceService extends AbstractDomainReferenceService<User> {

  public UserReferenceService(
      DomainRepository<User> repository) {
    super(repository);
  }

  @Override
  protected DiscodeitException notFoundException(UUID id) {
    return new UserException(UserErrorCode.USERID_NOT_FOUND, id);
  }

  @Override
  protected DiscodeitException notFoundException(Collection<UUID> ids) {
    return new UserException(UserErrorCode.USERID_NOT_FOUND, Map.of("id", ids));
  }
}

package com.sprint.mission.discodeit.auth.permission.evaluator;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionEvaluator extends AbstractPermissionEvaluator<User> {

  public UserPermissionEvaluator(UserRepository repository) {
    super("User", repository);
  }
}

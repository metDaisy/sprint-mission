package com.sprint.mission.discodeit.notification.application.mapper;

import com.sprint.mission.discodeit.common.mapper.config.GlobalMapperConfig;
import com.sprint.mission.discodeit.notification.domain.entity.Notification;
import com.sprint.mission.discodeit.user.domain.entity.User;
import java.util.Collections;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GlobalMapperConfig.class)
public interface NotificationDomainMapper {

  Notification toEntityFrom(User receiver, String title, String content);

  default List<Notification> toEntityFrom(List<User> receivers, String title, String content) {
    if (receivers == null || receivers.isEmpty()) {
      return Collections.emptyList();
    }
    return receivers.stream()
        .map(receiver -> toEntityFrom(receiver, title, content))
        .toList();
  }
}

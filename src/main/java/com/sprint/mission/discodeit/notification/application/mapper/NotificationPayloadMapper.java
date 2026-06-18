package com.sprint.mission.discodeit.notification.application.mapper;

import com.sprint.mission.discodeit.common.mapper.PayloadMapper;
import com.sprint.mission.discodeit.common.mapper.config.GlobalMapperConfig;
import com.sprint.mission.discodeit.common.payload.marker.PayloadUpdatedMarker;
import com.sprint.mission.discodeit.notification.domain.entity.Notification;
import com.sprint.mission.discodeit.notification.domain.payload.NotificationPayloadCreated;
import com.sprint.mission.discodeit.notification.domain.payload.NotificationPayloadDeleted;
import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class)
public abstract class NotificationPayloadMapper extends PayloadMapper<Notification> {

  @Override
  protected abstract NotificationPayloadCreated toCreated(Notification entity);

  @Override
  protected PayloadUpdatedMarker toUpdated(Notification entity) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected abstract NotificationPayloadDeleted toDeleted(Notification entity);
}

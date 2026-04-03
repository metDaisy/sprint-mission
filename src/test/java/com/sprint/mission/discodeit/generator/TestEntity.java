package com.sprint.mission.discodeit.generator;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.fixture.ChannelFixture;
import com.sprint.mission.discodeit.fixture.MessageFixture;
import com.sprint.mission.discodeit.fixture.UserFixture;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestEntity {

  @Autowired
  private EntityManager em;

  public User generatorUser() {
    User user = UserFixture.createEntity();
    return persistAndFlush(user);
  }

  public Channel generatorChannel() {
    Channel channel = ChannelFixture.createPublic();
    return persistAndFlush(channel);
  }

  public Message generatorMessage() {
    Message message = MessageFixture.createEntity();
    return persistAndFlush(message);
  }

  private <T> T persistAndFlush(T entity) {
    em.persist(entity);
    em.flush();
    return entity;
  }
}

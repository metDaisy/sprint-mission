package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.config.HibernateConfig;
import com.sprint.mission.discodeit.config.QueryDslConfig;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.fixture.BinaryContentFixture;
import com.sprint.mission.discodeit.fixture.ChannelFixture;
import com.sprint.mission.discodeit.fixture.UserFixture;
import com.sprint.mission.discodeit.fixture.UserStatusFixture;
import com.sprint.mission.discodeit.inspector.QueryInspector;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.mapper.BinaryContentMapperImpl;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.mapper.ChannelMapperImpl;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.mapper.UserMapperImpl;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Import({QueryDslConfig.class, HibernateConfig.class, QueryInspector.class})
@DataJpaTest
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = Replace.NONE)
public abstract class BaseRepositoryTest {

  @Autowired
  protected EntityManager em;
  @Autowired
  protected QueryInspector queryInspector;

  protected UserMapper userMapper;
  protected ChannelMapper channelMapper;
  protected BinaryContentMapper binaryContentMapper;
  protected List<User> users = new ArrayList<>();
  protected List<Channel> channels = new ArrayList<>();
  protected List<Message> messages = new ArrayList<>();
  protected List<BinaryContent> binaryContents = new ArrayList<>();
  protected List<UserStatus> userStatuses = new ArrayList<>();

  protected void initMappers() {
    binaryContentMapper = new BinaryContentMapperImpl();
    userMapper = new UserMapperImpl(binaryContentMapper);
    channelMapper = new ChannelMapperImpl();
  }

  protected void initUsers(UserRepository repository) {
    for (int i = 0; i < 10; i++) {
      User user = getUser();
      users.add(user);
      binaryContents.add(user.getProfile());
      userStatuses.add(user.getStatus());
    }
    repository.saveAllAndFlush(users);
  }

  protected void initPublicChannels(ChannelRepository repository) {
    for (int i = 0; i < 10; i++) {
      Channel channel = getPublicChannel();
      channels.add(channel);
    }
    repository.saveAllAndFlush(channels);
  }

  protected void initPrivateChannel(ChannelRepository repository) {
    repository.saveAndFlush(getPrivateChannel());
  }

  protected User getUser() {
    UserCreateRequest request = UserFixture.createRequest();
    UserStatus status = UserStatusFixture.createOnline();
    BinaryContent profile = BinaryContentFixture.createEntity();
    return userMapper.toEntityFrom(request, status, profile);
  }

  protected Channel getPublicChannel() {
    PublicChannelCreateRequest request = ChannelFixture.createPublicRequest();
    return channelMapper.toEntityFrom(request);
  }

  protected Channel getPrivateChannel() {
    return channelMapper.toEntityFrom(ChannelType.PRIVATE, users);
  }

  protected void flushAndClear() {
    em.flush();
    em.clear();
  }

  protected void ensureQueryCount(int count) {
    Assertions.assertThat(queryInspector.getCount()).isEqualTo(count);
  }

  protected boolean compareInstant(Instant a, Instant b) {
    return toTruncated(a).equals(toTruncated(b));
  }

  private Instant toTruncated(Instant time) {
    return time.truncatedTo(ChronoUnit.MILLIS);
  }
}

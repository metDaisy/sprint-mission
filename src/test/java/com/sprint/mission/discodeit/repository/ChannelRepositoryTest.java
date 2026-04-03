package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.fixture.ChannelFixture;
import com.sprint.mission.discodeit.fixture.ReadStatusFixture;
import com.sprint.mission.discodeit.fixture.UserFixture;
import com.sprint.mission.discodeit.generator.TestEntity;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ChannelRepositoryTest extends BaseRepositoryTest {

  @Autowired
  private ChannelRepository channelRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ReadStatusRepository readStatusRepository;

  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private TestEntity testEntity;

  @BeforeEach
  void setUp() {
    queryInspector.clear();
  }

  @Test
  @DisplayName(
      "public channel 생성 후 조회 성공한다\n"
          + "조회 시 query 1개 생성한다"
  )
  void success_to_create_and_find_public() {
    Channel actual = testEntity.generatorPublicChannel();
    ensureQueryCount(1);
    clear();
    Channel expected = channelRepository.findByIdWithLastMessageAt(actual.getId()).orElseThrow();
    ensureQueryCount(1);
    Assertions.assertThat(actual)
        .usingRecursiveComparison()
        .withEqualsForType(this::compareInstant, Instant.class)
        .isEqualTo(expected);
  }

  @Test
  @DisplayName(
      "private channel 생성 후 조회 성공한다\n"
          + "조회 시 query 1개 생성한다"
  )
  void success_to_create_and_find_private() {
    Channel actual = testEntity.generatorPrivateChannel();
    ensureQueryCount(1);
    clear();
    Channel expected = channelRepository.findByIdWithLastMessageAt(actual.getId()).orElseThrow();
    ensureQueryCount(1);
    Assertions.assertThat(actual)
        .usingRecursiveComparison()
        .withEqualsForType(this::compareInstant, Instant.class)
        .isEqualTo(expected);
  }

  @Test
  @DisplayName(
      """
          public, private channel 을 생성한다
          모든 채널을 조회한다
          조회에 대한 query 1개 생성된다
          """
  )
  void findAllWithLastMessageAt() {
    List<Channel> actual = List.of(ChannelFixture.createPublic(), ChannelFixture.createPublic(),
        ChannelFixture.createPrivate(), ChannelFixture.createPrivate());
    channelRepository.saveAllAndFlush(actual);
    ensureQueryCount(1);
    clear();
    List<Channel> expected = channelRepository.findAllWithLastMessageAt();
    ensureQueryCount(1);
    Assertions.assertThat(actual)
        .usingRecursiveComparison()
        .ignoringCollectionOrder()
        .withEqualsForType(this::compareInstant, Instant.class)
        .isEqualTo(expected);
  }

  @Test
  @DisplayName(
      "user 생성 및 private channel을 생성한다\n"
          + "user가 볼 수 있는 모든 channel(모든 public, user 가 속한 private channel)을 조회한다"
  )
  void findVisibleToWithLastMessageAt() {
    List<User> users = UserFixture.createEntities();
    userRepository.saveAll(users);
    List<Channel> publicChannels = ChannelFixture.createPublicChannels();
    List<Channel> privateChannels = ChannelFixture.createPrivateChannels();
    channelRepository.saveAll(publicChannels);
    channelRepository.saveAll(privateChannels);
    List<ReadStatus> readStatuses = getReadStatuses(privateChannels, users);
    readStatusRepository.saveAll(readStatuses);
    em.flush();
    ensureQueryCount(5);
    clear();

    UUID userId = users.get(0).getId();
    List<UUID> expectedPublicChannelIds = publicChannels.stream()
        .map(Channel::getId)
        .toList();
    List<UUID> expectedPrivateChannelIds = readStatuses.stream()
        .filter(rs -> rs.getUser().getId().equals(userId))
        .map(rs -> rs.getChannel().getId())
        .toList();

    List<Channel> actualVisibleChannels = channelRepository.findVisibleToWithLastMessageAt(userId);
    ensureQueryCount(1);
    Assertions.assertThat(actualVisibleChannels)
        .extracting(Channel::getId)
        .hasSize(expectedPublicChannelIds.size() + expectedPrivateChannelIds.size())
        .containsAll(expectedPublicChannelIds)
        .containsAll(expectedPrivateChannelIds);
  }

  private List<ReadStatus> getReadStatuses(
      List<Channel> privateChannels, List<User> users) {
    List<ReadStatus> readStatuses = new ArrayList<>();
    for (int i = 0; i < privateChannels.size(); i++) {
      User user = users.get(i);
      Channel channel = privateChannels.get(i);
      readStatuses.add(ReadStatusFixture.createEntity(user, channel));
    }
    return readStatuses;
  }
}

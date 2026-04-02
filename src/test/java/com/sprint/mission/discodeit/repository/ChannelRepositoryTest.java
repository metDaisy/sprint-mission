package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.fixture.ChannelFixture;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

class ChannelRepositoryTest extends BaseRepositoryTest {

  @Autowired
  private ChannelRepository channelRepository;

  @Autowired
  private UserRepository userRepository;

  @BeforeEach
  void setUp() {
    initMappers();
    initUsers(userRepository);
    initPublicChannels(channelRepository);
    initPrivateChannel(channelRepository);
    flushAndClear();
    queryInspector.clear();
  }

  @Test
  @DisplayName("public channel 생성 후 성공")
  void success_to_create_and_find_public() {
    PublicChannelCreateRequest request = ChannelFixture.createPublicRequest();
    Channel channel = channelMapper.toEntityFrom(request);
    channelRepository.save(channel);
    flushAndClear();
    ensureQueryCount(1);
    Channel expected = channelRepository.findById(channel.getId()).orElseThrow();
    Assertions.assertThat(expected)
        .extracting(Channel::getDescription, Channel::getName, Channel::getType)
        .containsExactly(request.getDescription(), request.getName(), request.getType());
    ensureQueryCount(2);
  }

  @Test
  @DisplayName("private channel 생성 후 조회 성공")
  void success_to_create_and_find_private() {
    Channel channel = channelMapper.toEntityFrom(ChannelType.PRIVATE, users);
    channelRepository.save(channel);
    flushAndClear();
    queryInspector.clear();
    Channel expected = channelRepository.findById(channel.getId()).orElseThrow();
    Assertions.assertThat(expected)
        .extracting(Channel::getType, Channel::getName, Channel::getDescription)
        .containsExactly(ChannelType.PRIVATE, null, null);
    ensureQueryCount(1);
    Assertions.assertThat(expected.getReadStatuses())
        .usingRecursiveAssertion()
        .isEqualTo(channel.getReadStatuses());
    ensureQueryCount(2);
  }

  @Test
  @DisplayName("존재하지 않는 유저가 포함된다면 private channel 생성은 실패한다")
  void fail_to_create_private_with_no_existing_users() {
    List<User> participants = List.of(userRepository.getReferenceById(UUID.randomUUID()));
    Channel channel = channelMapper.toEntityFrom(ChannelType.PRIVATE, participants);
    Assertions.assertThatThrownBy(() -> channelRepository.saveAndFlush(channel))
        .isInstanceOf(DataIntegrityViolationException.class);
  }

  @Test
  @DisplayName("public channel 생성 후 QueryDSL 로 작성한 findByIdWithLastMsgAt 로 조회 성공")
  void success_to_create_public_and_findByIdWithLastMsgAt() {
    PublicChannelCreateRequest request = ChannelFixture.createPublicRequest();
    Channel channel = channelMapper.toEntityFrom(request);
    channelRepository.save(channel);
    flushAndClear();
    ensureQueryCount(1);
    Channel expected = channelRepository.findByIdWithLastMsgAt(channel.getId()).orElseThrow();
    Assertions.assertThat(expected)
        .usingRecursiveComparison()
        .withEqualsForType(this::compareInstant, Instant.class)
        .isEqualTo(channel);
    ensureQueryCount(3);
  }

  // todo: remove Set<ReadStatus> in Channel
  @Test
  @DisplayName("private channel 생성 후 findByIdWithLastMsgAt 로 조회 성공")
  void success_to_create_private_and_findByIdWithLastMsgAt() {
    List<UUID> userIds = users.stream().map(User::getId).toList().subList(0, 1);
    List<User> participants = userRepository.getReferenceById(userIds);
    Channel channel = channelMapper.toEntityFrom(ChannelType.PRIVATE, participants);
    channelRepository.save(channel);
    flushAndClear();
    queryInspector.clear();
    Channel expected = channelRepository.findByIdWithLastMsgAt(channel.getId()).orElseThrow();
    Assertions.assertThat(expected)
        .usingRecursiveComparison()
        .ignoringCollectionOrder()
        .ignoringFields("readStatuses.user")
        .withEqualsForType(this::compareInstant, Instant.class)
        .isEqualTo(channel);
    ensureQueryCount(3);
  }

  @Disabled
  @Test
  void findAllWithLastMsgAt() {
  }

  @Disabled
  @Test
  void findVisibleToWithLastMsgAt() {
  }
}

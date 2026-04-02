package com.sprint.mission.discodeit.fixture;

import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.entity.ChannelType;
import net.datafaker.Faker;

public final class ChannelFixture {

  private static final Faker faker = new Faker();

  private ChannelFixture() {
  }

  public static PublicChannelCreateRequest createPublicRequest() {
    return new PublicChannelCreateRequest(ChannelType.PUBLIC, getName(), getDescription());
  }

  private static String getDescription() {
    return faker.text().text();
  }

  private static String getName() {
    return faker.funnyName().name();
  }
}

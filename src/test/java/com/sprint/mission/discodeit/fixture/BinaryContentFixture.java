package com.sprint.mission.discodeit.fixture;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.entity.BinaryContent;

public final class BinaryContentFixture {

  private BinaryContentFixture() {
  }

  public static BinaryContent createEntity() {
    return new BinaryContent("pepe", 123455L, "png", new byte[]{1, 3, 4});
  }

  public static BinaryContentDto createDto() {
    BinaryContent entity = createEntity();
    return new BinaryContentDto(entity.getFileName(), entity.getSize(), entity.getContentType(),
        entity.getBytes());
  }
}

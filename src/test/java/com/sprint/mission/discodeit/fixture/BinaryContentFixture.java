package com.sprint.mission.discodeit.fixture;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import net.datafaker.Faker;
import org.jspecify.annotations.NonNull;

public final class BinaryContentFixture {
  private static final Faker faker = new Faker();

  private BinaryContentFixture() {
  }

  public static BinaryContent createEntity() {
    return new BinaryContent(getFileName(), getSize(), faker.file().extension(), getBytes());
  }

  public static BinaryContentDto createDto() {
    BinaryContent entity = createEntity();
    return BinaryContentDto.builder()
        .fileName(entity.getFileName())
        .size(entity.getSize())
        .contentType(entity.getContentType())
        .bytes(entity.getBytes())
        .build();
  }

  private static long getSize() {
    return faker.number().randomNumber();
  }

  private static String getFileName() {
    return faker.file().fileName();
  }

  private static byte[] getBytes() {
    return faker.lorem().paragraph().getBytes();
  }
}

package com.sprint.mission.discodeit.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sprint.mission.discodeit.entity.ChannelType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class PublicChannelCreateRequest implements Serializable {

  @Serial
  private static final long serialVersionUID = 12345L;

  @NotNull
  private final ChannelType type;

  @NotEmpty
  private final String name;

  @NotEmpty
  private final String description;

  @JsonCreator
  public PublicChannelCreateRequest(
      @JsonProperty("type") ChannelType type,
      @JsonProperty("name") String name,
      @JsonProperty("description") String description) {
    this.type = type;
    this.name = name;
    this.description = description;
  }
}

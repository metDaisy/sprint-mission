package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "channels")
public class Channel extends BaseUpdatableEntity {

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ChannelType type;

  @Column
  private String name;

  @Column
  private String description;

  @Transient
  private Instant lastMessageAt;

  @OneToMany(
      mappedBy = "channel",
      orphanRemoval = true,
      cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
  private Set<ReadStatus> readStatuses = new LinkedHashSet<>();

  @Builder
  public Channel(ChannelType type, String name, String description) {
    this.type = type;
    this.name = name;
    this.description = description;
  }

  public void addParticipants(List<User> participants) {
    List<ReadStatus> readStatuses = participants.stream()
        .map(user -> new ReadStatus(user, this, Instant.now()))
        .toList();
    this.readStatuses.addAll(readStatuses);
  }
}

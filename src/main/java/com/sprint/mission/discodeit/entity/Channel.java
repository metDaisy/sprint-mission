package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.ChannelDto;
import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "channels")
public class Channel extends BaseUpdatableEntity<ChannelDto> {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChannelType type;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Setter
    @Transient
    private Instant lastMessageAt;

    @Builder
    public Channel(ChannelType type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }

    @Override
    public void update(ChannelDto dto) {
        updateIfChanged(name, dto.name(), val -> name = val);
        updateIfChanged(description, dto.description(), val -> description = val);
    }
}

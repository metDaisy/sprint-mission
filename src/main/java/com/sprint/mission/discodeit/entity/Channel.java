package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.ChannelDto;
import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.PrivateChannelCreateDto;
import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.PublicChannelCreateDto;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserResponse;
import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    private List<UserResponse> participants = new ArrayList<>();

    public Channel(PublicChannelCreateDto dto) {
        this.name = dto.name();
        this.description = dto.description();
        this.type = dto.type();
    }

    public Channel(PrivateChannelCreateDto dto) {
        participants = dto.participants();
        this.type = dto.type();
    }

    @Override
    public void update(ChannelDto dto) {
        updateIfChanged(name, dto.name(), val -> name = val);
        updateIfChanged(description, dto.description(), val -> description = val);
    }

    public boolean matchChannelType(ChannelType type) {
        return this.type == type;
    }

    public boolean isVisibleTo(UUID userId) {
        return type == ChannelType.PUBLIC ||
                participants.stream().anyMatch(u -> u.id().equals(userId));
    }
}

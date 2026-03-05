package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "read_statuses")
public class ReadStatusV2 extends BaseUpdatableEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserV2 user;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private ChannelV2 channel;

    @Column
    private Instant lastReadAt;
}

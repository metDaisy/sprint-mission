package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.dto.readstatus.ReadStatusServiceDTO.ReadStatusUpdateDto;
import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@Entity
@Table(name = "read_statuses")
public class ReadStatus extends BaseUpdatableEntity<ReadStatusUpdateDto> {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @Column
    private Instant lastReadAt;

    public ReadStatus(User user, Channel channel, Instant lastReadAt) {
        this.user = user;
        this.channel = channel;
        this.lastReadAt = lastReadAt;
    }

    @Override
    public void update(ReadStatusUpdateDto dto) {
        updateIfChanged(lastReadAt, dto.lastReadAt(), val -> lastReadAt = val);
    }
}

package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.dto.userstatus.UserStatusServiceDTO.UserStatusDto;
import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "user_statuses")
public class UserStatus extends BaseUpdatableEntity<UserStatusDto> {
    private static final Long ACTIVE_THRESHOLD = 300L;

    @Setter
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private Instant lastActiveAt;

    public UserStatus(User user, Instant lastActiveAt) {
        this.user = user;
        this.lastActiveAt = lastActiveAt;
        user.setStatus(this);
    }

    public boolean isOnline() {
        return Duration.between(lastActiveAt, Instant.now()).getSeconds() < ACTIVE_THRESHOLD;
    }

    @Override
    public void update(UserStatusDto updateDto) {
        updateIfChanged(lastActiveAt, updateDto.lastActiveAt(), val -> lastActiveAt = val);
    }
}

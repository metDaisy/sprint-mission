package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "users")
public class UserV2 extends BaseUpdatableEntity {
    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @OneToOne
    @JoinColumn(name = "profile_id")
    private BinaryContentV2 profile;

    @Setter
    @OneToOne(mappedBy = "users")
    private UserStatusV2 status = new UserStatusV2();
}

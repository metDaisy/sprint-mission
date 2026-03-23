package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserCreateDto;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserUpdateDto;
import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseUpdatableEntity<UserUpdateDto> {
    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private BinaryContent profile;

    @Setter
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserStatus status;

    public User(String username, String email, String password,
                BinaryContent profile, UserStatus status) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profile = profile;
        this.status = status;
        this.status.setUser(this);
    }

    public User(UserCreateDto dto, BinaryContent profile, UserStatus status) {
        this(dto.username(), dto.email(), dto.password(), profile, status);
    }

    public boolean isOnline() {
        return status.isOnline();
    }

    @Override
    public void update(UserUpdateDto dto) {
        updateIfChanged(username, dto.username(), val -> username = val);
        updateIfChanged(email, dto.email(), val -> email = val);
        updateIfChanged(password, dto.password(), val -> password = val);
        updateIfChanged(
                profile,
                Optional.ofNullable(dto.profile())
                        .map(BinaryContent::new)
                        .orElse(null),
                val -> profile = val);
    }
}

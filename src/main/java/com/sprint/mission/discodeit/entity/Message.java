package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "messages")
public class Message extends BaseUpdatableEntity {
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User user;

    @OneToMany
    private List<BinaryContent> attachments = new ArrayList<>();

    public void addAttachment(BinaryContent attachment) {
        this.attachments.add(attachment);
    }
}

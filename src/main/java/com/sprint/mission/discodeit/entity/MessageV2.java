package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "messages")
public class MessageV2 extends BaseUpdatableEntity {
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private ChannelV2 channel;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserV2 user;

    @OneToMany
    private List<BinaryContentV2> attachments = new ArrayList<>();

    public void addAttachment(BinaryContentV2 attachment) {
        this.attachments.add(attachment);
    }
}

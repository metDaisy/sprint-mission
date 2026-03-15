package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.dto.message.MessageServiceDTO.MessageUpdateDto;
import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Message extends BaseUpdatableEntity<MessageUpdateDto> {
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany
    @JoinTable(
            name = "message_attachments",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "attachment_id")
    )
    private List<BinaryContent> attachments = new ArrayList<>();

    @Builder
    public Message(String content, Channel channel, User author, List<BinaryContent> attachments) {
        this.content = content;
        this.channel = channel;
        this.author = author;
        this.attachments = attachments;
    }

    @Override
    public void update(MessageUpdateDto dto) {
        updateIfChanged(content, dto.content(), val -> content = val);
    }

    public void addAttachment(BinaryContent attachment) {
        this.attachments.add(attachment);
    }
}

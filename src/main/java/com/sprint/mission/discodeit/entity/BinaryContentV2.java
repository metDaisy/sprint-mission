package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseEntityV2;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "binary_contents")
public class BinaryContentV2 extends BaseEntityV2 {
    @Column(nullable = false)
    private String fileName;
    
    @Column(nullable = false)
    private Long size;

    @Column(nullable = false, length = 100)
    private String contentType;

    @Column(nullable = false)
    private byte[] bytes;
}
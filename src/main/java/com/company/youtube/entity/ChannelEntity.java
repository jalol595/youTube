package com.company.youtube.entity;

import com.company.youtube.enums.ChannelStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "channel")
public class ChannelEntity {
    // id(uuid),name,photo,description,status (ACTIVE, BLOCK),banner,profile_id,key
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChannelStatus status;


    @JoinColumn(name = "photo_id")
    @OneToOne(fetch = FetchType.LAZY)
    private AttachEntity photo;

    @JoinColumn(name = "baner_id")
    @OneToOne(fetch = FetchType.LAZY)
    private AttachEntity baner;


    @JoinColumn(nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private ProfileEntity profile;

    @Column
    private String websiteUrl;

    @Column
    private String telegramUrl;

    @Column
    private String instagramUrl;

    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean visible = Boolean.TRUE;

    public ChannelEntity(String id) {
        this.id = id;
    }

    public ChannelEntity() {
    }
}

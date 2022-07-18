package com.company.youtube.entity;

import com.company.youtube.enums.TypeVideo;
import com.company.youtube.enums.VideoPlaylistStatus;
import com.company.youtube.enums.VideoPublish;
import com.company.youtube.enums.VideoStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "video")
public class VideoEntity {
//    id(uuid), (key), preview_attach_id,title,category_id,attach_id,created_date,published_date,status(private,public),
//     type(video,short),view_count,shared_count,description,channel_id,(like_count,dislike_count)

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "preview_attach_id")
    private String previewId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preview_attach_id",nullable = false,insertable = false,updatable = false)
    private AttachEntity preview;

    @Column(nullable = false)
    private String title;

    @Column(name = "attach_id")
    private String photoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id",nullable = false,insertable = false,updatable = false)
    private AttachEntity photo;

    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "published_date")
    private LocalDateTime publishDate;

    @Column(name = "publish")
    private VideoPublish publish;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VideoStatus status;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeVideo type;

    @Column(name = "view_count")
    private Integer viewCount=0;

    @Column(name = "shared_count")
    private Integer sharedCount=0;

    @Column(nullable = false)
    private String description;

    @Column(name = "like_count")
    private Integer likeCount=0;

    @Column(name = "dis_like_count")
    private Integer disLikeCount=0;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private ChannelEntity channel;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baner_id")
    private AttachEntity baner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;


    @Column(nullable = false)
    private Boolean visible = Boolean.TRUE;

    public VideoEntity(String id) {
        this.id = id;
    }

    public VideoEntity(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public VideoEntity() {
    }
}

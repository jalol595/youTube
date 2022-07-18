package com.company.youtube.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class CommentEntity {
//    id,profile_id,video_id,content,reply_id (like_count,dislike_count)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(nullable = false, name = "profile_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProfileEntity profile;


    @JoinColumn(nullable = false, name = "video_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private VideoEntity video;


    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @JoinColumn(name = "reply_id")
    @ManyToOne( fetch = FetchType.LAZY)
    private CommentEntity replyId;

    @Column(name = "like_count")
    private Integer likeCount=0;

    @Column(name = "dis_like_count")
    private Integer disLikeCount=0;

    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "updated_date")
    private LocalDateTime updateDate;

    @Column(nullable = false)
    private Boolean visible = Boolean.TRUE;



    public CommentEntity(Integer id) {
        this.id = id;
    }
}

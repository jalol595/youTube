package com.company.youtube.entity;


import com.company.youtube.enums.VideoLikeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "video_like")
public class VideoLikeEntity {
//    id,profile_id,video_id,created_date,type(Like,Dislike)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "video_like_status")
    @Enumerated(EnumType.STRING)
    private VideoLikeStatus likeStatus;

    @JoinColumn(name = "video_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private VideoEntity video;

    @Column(name = "profile_id")
    private Integer profileId;

    @JoinColumn(name = "profile_id",nullable = false,insertable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ProfileEntity profile;



    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();


}

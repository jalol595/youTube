package com.company.youtube.entity;


import com.company.youtube.enums.CommentLikeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment_like")
public class CommentLikeEntity {
//    id,profile_id,comment_id,created_date,type(Like,Dislike)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "comment_like_status")
    @Enumerated(EnumType.STRING)
    private CommentLikeStatus likeStatus;

    @JoinColumn(name = "commet_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CommentEntity comment;


    @JoinColumn(name = "profile_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProfileEntity profile;



    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();


}

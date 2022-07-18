package com.company.youtube.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "video_category")
public class VideoCategoryEntity {
/*    id,video_id,tag_id,created_date*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private VideoEntity video;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;



    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean visible = Boolean.TRUE;


}

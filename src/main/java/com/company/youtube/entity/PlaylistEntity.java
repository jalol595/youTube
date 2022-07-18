package com.company.youtube.entity;

import com.company.youtube.enums.ChannelStatus;
import com.company.youtube.enums.PlaylistStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "playlist")
public class PlaylistEntity {
    /*  id,channel_id,name,description,status(private,public),order_num*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "channel_id")
    private ChannelEntity channel;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlaylistStatus status;

    @Column(name = "order_num")
    private Integer orderNum;

    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean visible = Boolean.TRUE;

    public PlaylistEntity() {
    }

    public PlaylistEntity(Integer id) {
        this.id = id;
    }
}

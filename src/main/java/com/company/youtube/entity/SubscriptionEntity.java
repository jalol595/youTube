package com.company.youtube.entity;

import com.company.youtube.enums.NotificationType;
import com.company.youtube.enums.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscription")
public class SubscriptionEntity {
    //  id,profile_id,channel_id,created_date,status (active,block),notification_type(All,Personalized,Non)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "profile_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProfileEntity profile;


    @JoinColumn(name = "channel_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ChannelEntity channel;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status = SubscriptionStatus.ACTIVE;

    @Column()
    @Enumerated(EnumType.STRING)
    private NotificationType type;


    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean visible = Boolean.TRUE;


    public SubscriptionEntity(Integer id) {
        this.id = id;
    }
}

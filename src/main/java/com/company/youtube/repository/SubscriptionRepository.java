package com.company.youtube.repository;

import com.company.youtube.dto.subscraption.SubscriptionShortInfoDTO;
import com.company.youtube.entity.ChannelEntity;
import com.company.youtube.entity.ProfileEntity;
import com.company.youtube.entity.SubscriptionEntity;
import com.company.youtube.enums.NotificationType;
import com.company.youtube.enums.SubscriptionStatus;
import com.company.youtube.mapper.SubscriptionShortInfoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, Integer> {


    Optional<SubscriptionEntity> findByIdAndVisibleTrue(Integer integer);

    Optional<SubscriptionEntity> findByChannelAndProfileAndVisibleTrue(ChannelEntity channel, ProfileEntity profile);

    @Modifying
    @Transactional
    @Query("update  SubscriptionEntity s set s.status=:status where s.id=:id")
    void updateStatus(@Param("status") SubscriptionStatus status,  @Param("id")  Integer id);

    @Modifying
    @Transactional
    @Query("update  SubscriptionEntity s set s.type=:type where s.id=:id")
    void updateNotification(@Param("type") NotificationType type,  @Param("id") Integer id);

    //          id,channel(id,name,photo(id,url)),notification_type
    @Query(value = "select s.id as id, ch.id as channelId, ch.photo_id as channelPhoto, s.type as notification " +
            "  From subscription as s  " +
            "  inner join profile as p on p.id = s.profile_id " +
            "  inner join channel as ch on ch.id = s.channel_id " +
            "  where s.status = 'ACTIVE' and p.id = :id ",
            nativeQuery = true) 
    List<SubscriptionShortInfoRepository> userSubscriptionList(@Param("id") Integer id);



}

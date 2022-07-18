package com.company.youtube.repository;

import com.company.youtube.entity.AttachEntity;
import com.company.youtube.entity.CategoryEntity;
import com.company.youtube.entity.ChannelEntity;
import com.company.youtube.enums.ChannelStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends PagingAndSortingRepository<ChannelEntity, String> {

    Optional<ChannelEntity> findByIdAndStatusAndVisibleTrue(String id, ChannelStatus status);

    boolean existsByNameAndVisible(String name, Boolean b);

    @Modifying
    @Transactional
    @Query("update  ChannelEntity c set c.photo=?1 where c.id=?2")
    void deletePhoto(AttachEntity photoId, String id);

    @Modifying
    @Transactional
    @Query("update  ChannelEntity c set c.baner=?1 where c.id=?2")
    void deletePhotoBaner(AttachEntity photoId, String id);

    @Modifying
    @Transactional
    @Query("update  ChannelEntity c set c.photo=?1 where c.id=?2")
    void updatePhoto(AttachEntity photoId, String id);

    @Modifying
    @Transactional
    @Query("update  ChannelEntity c set c.baner=?1 where c.id=?2")
    void updatePhotoBaner(AttachEntity photoId, String id);

    @Modifying
    @Transactional
    @Query("update  ChannelEntity c set c.status='BLOCK' where c.id=?1")
    void updateStatusById( String id);


    @Query("FROM ChannelEntity c where c.visible = true and c.status = 'ACTIVE' and c.profile.id =?1")
    List<ChannelEntity> getList(Integer id);

}

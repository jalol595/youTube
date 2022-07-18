package com.company.youtube.repository;

import com.company.youtube.entity.*;
import com.company.youtube.mapper.VideoTagRepositoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface VideoTagRepository extends JpaRepository<VideoTagEntity, Integer> {
    List<VideoTagEntity> findAllByVideo(VideoEntity video);

    Optional<VideoTagEntity> findByVideo(VideoEntity video);


    Optional<VideoTagEntity> findByVideoAndTag(VideoEntity video, TagEntity tagEntity);

    @Modifying
    @Transactional
    @Query("update  VideoTagEntity v set v.visible=FALSE where v.id=?1")
    void delete(Integer id);

    @Query(value = " select vt.id as id, v.created_date as createdDate, v.id as videoId, " +
            "   t.id as tagId, t.name as tagName " +
            "   from video_tag as vt " +
            "   inner join video as v on v.id= vt.video_id " +
            "   inner join tag as t on t.id = vt.tag_id  " +
            "   where v.id =:videoId ",
            nativeQuery = true)
    List<VideoTagRepositoryDTO> getByVideoId(@Param("videoId") String videoId);

}

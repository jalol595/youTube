package com.company.youtube.repository;


import com.company.youtube.entity.ProfileEntity;
import com.company.youtube.entity.VideoLikeEntity;
import com.company.youtube.entity.VideoTagEntity;
import com.company.youtube.enums.ProfileRole;
import com.company.youtube.mapper.VideoLikeInfoRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface VideoLikeRepository extends CrudRepository<VideoLikeEntity, Integer> {

    Optional<VideoLikeEntity> findByVideoAndProfile(VideoLikeEntity videoLike, ProfileEntity profile);

    @Query("FROM VideoLikeEntity  v where  v.video.id=:id and v.profile.id =:profileId")
    Optional<VideoLikeEntity> findExists(String id, Integer profileId);

    @Transactional
    @Modifying
    @Query("DELETE FROM VideoLikeEntity v where  v.video.id=:id and v.profile.id =:profileId")
    void delete(String id, Integer profileId);

    //    id,video(id,name,key,channel(id,name),duration),preview_attach(id,url)
    @Query(value = " select vl.id as id, v.id as videoId, v.title as videoTitle, v.preview_attach_id as videoPreview,  " +
            "  ch.id as channelId, ch.name as channelName " +
            "  from video_like as vl " +
            "  inner join video as v on v.id = vl.video_id " +
            "  inner join channel as ch on ch.id = v.channel_id " +
            "  inner join profile as p on p.id = vl.profile_id " +
            "  where p.id = :id " +
            "  ORDER BY vl.created_date DESC ",
            nativeQuery = true)
    List<VideoLikeInfoRepository> userLikedVideoList(@Param("id") Integer id);


}

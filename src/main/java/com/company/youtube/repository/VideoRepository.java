package com.company.youtube.repository;

import com.company.youtube.entity.AttachEntity;
import com.company.youtube.entity.ChannelEntity;
import com.company.youtube.entity.VideoEntity;
import com.company.youtube.enums.ChannelStatus;
import com.company.youtube.enums.TypeVideo;
import com.company.youtube.enums.VideoStatus;
import com.company.youtube.mapper.VideoPlaylistInfo;
import com.company.youtube.mapper.video.VideoFullInfo;
import com.company.youtube.mapper.video.VideoShortInfoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface VideoRepository extends CrudRepository<VideoEntity, String> {

    Optional<VideoEntity> findByTitleAndPreviewAndVisibleTrue(String title, AttachEntity preview);

    Optional<VideoEntity> findByIdAndVisibleTrue(String id);



    @Modifying
    @Transactional
    @Query("update  VideoEntity v set v.title=?1, v.description=?2, v.type=?3, v.channel.id=?4 where v.id=?5")
    void updateDeatil(String title, String description, TypeVideo type, String channelId, String id);


    @Modifying
    @Transactional
    @Query("update  VideoEntity v set v.photo=?1 where v.id=?2")
    void deletePhoto(AttachEntity photoId, String id);

    @Modifying
    @Transactional
    @Query("update  VideoEntity c set c.baner=?1 where c.id=?2")
    void deletePhotoBaner(AttachEntity photoId, String id);

    @Modifying
    @Transactional
    @Query("update  VideoEntity v set v.photo=?1 where v.id=?2")
    void updatePhoto(AttachEntity photoId, String id);

    @Modifying
    @Transactional
    @Query("update  VideoEntity v set v.baner=?1 where v.id=?2")
    void updatePhotoBaner(AttachEntity photoId, String id);

    @Modifying
    @Transactional
    @Query("update  VideoEntity v set v.status=?1 where v.id=?2")
    void changeStatus(VideoStatus status, String id);

    @Modifying
    @Transactional
    @Query("update VideoEntity v set v.viewCount =:count where v.id=:videoId")
    void updtecount(@Param("videoId") String videoId, @Param("count") Integer count);

    @Query(value = "select  v.id as id, v.title as title, v.view_count as viewCount, v.preview_attach_id as preview, v.publish_date as publishedDate, " +
            "  ch.id as channelId, ch.name as channelName, ch.photo_id as channelPhoto " +
            "  FROM video as v " +
            "  inner join channel as ch on ch.id = v.channel_id " +
            "  inner join video_category as vc on vc.video_id = v.id " +
            "  inner join category as c on c.id= vc.category_id " +
            "  Where c.id =:id ",
            nativeQuery = true)
    Page<VideoShortInfoRepository> getVideoPaginationbyCategoryId(@Param("id") Integer id, Pageable pageable);

    @Query(value = "select  v.id as id, v.title as title, v.view_count as viewCount, v.preview_attach_id as preview, v.publish_date as publishedDate, " +
            "  ch.id as channelId, ch.name as channelName, ch.photo_id as channelPhoto " +
            "  FROM video as v " +
            "  inner join channel as ch on ch.id = v.channel_id " +
            "  Where v.title =:title ",
            nativeQuery = true)
    List<VideoShortInfoRepository> getBytitle(@Param("title") String title);


    @Query(value = "select  v.id as id, v.title as title, v.view_count as viewCount, v.preview_attach_id as preview, v.publish_date as publishedDate, " +
            "   ch.id as channelId, ch.name as channelName, ch.photo_id as channelPhoto " +
            "  FROM video as v " +
            "  inner join channel as ch on ch.id = v.channel_id " +
            "  inner join video_tag as vt on vt.video_id = v.id " +
            "  Where vt.id =:tagId ",
            nativeQuery = true)
    Page<VideoShortInfoRepository> videoByTagIdWithPagination(@Param("tagId") Integer id, Pageable  pageable);



    @Query(value = " select pv.id as playListVideoId, pv.video_id as videoId, v.title as videoName " +
            " from playlist_video as pv " +
            " inner join video as v on v.id = pv.video_id " +
            " Where pv.playlist_id = :playlistId " +
            " order by  order_num asc limit 2", nativeQuery = true)
    public List<VideoPlaylistInfo> getVideoFoPlayList(@Param("playlistId") Integer playlistId);

    @Query(value = "select v.id as videoId, v.title as videoName, " +
            "             v.description as description, " +
            "             v.created_date as createdDate, " +
            "             v.view_count as viewCount, v.shared_count as sharedCount, " +
            "             v.preview_attach_id as previewId, " +
            "             v.attach_id as attachId, " +
            "             c.id as categoryId, c.name as categoryName, " +
            "             ch.id as channelId, ch.name as channelName, ch.photo_id as channelPhotoId, " +
            "             (select count(*) from video_like vl inner join video v " +
            "             on v.id = vl.video_id  where v.id =:videoId and type = 'LIKE') " +
            "             as videoLikeCount, " +
            "             (select count(*) from video_like vl inner join video v " +
            "             on v.id = vl.video_id  where v.id =:videoId and type = 'DISLIKE' ) " +
            "             as videoDislikeCount, " +
            "             (select type from video_like vl inner join video v " +
            "             on v.id = vl.video_id where v.id =:videoId and vl.profile_id=:profileId ) " +
            "             as isUserLiked " +
            "             from video as v " +
            "             inner join attach as a on a.id = v.attach_id " +
            "             inner join category as c on c.id = v.category_id " +
            "             inner join channel as ch on ch.id = v.channel_id " +
            "             where v.id=:videoId", nativeQuery = true)
    VideoFullInfo getByIdWithFullInfo(@Param("profileId") Integer profileId,
                                      @Param("videoId") String videoId);

}

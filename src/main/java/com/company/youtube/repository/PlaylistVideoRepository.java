package com.company.youtube.repository;

import com.company.youtube.entity.PlaylistEntity;
import com.company.youtube.entity.PlaylistVideoEntity;
import com.company.youtube.entity.VideoEntity;
import com.company.youtube.mapper.playlist.PlaylistShortInfoRepository;
import com.company.youtube.mapper.playlistVideo.PlaylistVideoInfoRepository;
import com.company.youtube.mapper.video.VideoShortInfoRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PlaylistVideoRepository extends CrudRepository<PlaylistVideoEntity, Integer> {

    @Modifying
    @Transactional
    @Query("update  PlaylistVideoEntity  p set p.orderNum=?1 where p.id=?2")
    void updateOerderNum(Integer id, Integer pId);

    Optional<PlaylistVideoEntity> findByIdAndPlaylistAndVideo(Integer id, PlaylistEntity playlist, VideoEntity video);


    @Modifying
    @Transactional
    @Query("update  PlaylistVideoEntity  p set p.visible=FALSE where p.id=?1")
    void updateVsibl(Integer pId);

    @Query(value = " SELECT plv.id as id, plv.order_num as orderNum, plv.created_date as createdDate, " +
            "  v.id as videoId, v.preview_attach_id as videoPreview, v.title as videoTitle, " +
            "  ch.id as channelId, ch.name as channelName " +
            "  FROM playlist_video as plv " +
            "  inner join video as v on v.id=plv.video_id " +
            "  inner  join channel as ch on ch.id = v.channel_id " +
            "  inner  join profile as p on p.id=v.profile_id" +
            "   where p.id=:id",
            nativeQuery = true)
    List<PlaylistVideoInfoRepository> getVideolistByplayListId(@Param("id") Integer id);

    @Query("select new VideoEntity(v.id,v.title) " +
            " from VideoEntity v " +
            " inner join PlaylistVideoEntity pv " +
            " on v.id=pv.video.id " +
            " where pv.playlist.id=?1 ")
    List<VideoEntity> findTop2ByPlaylistId(Integer playlistId);
}

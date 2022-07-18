package com.company.youtube.repository;

import com.company.youtube.entity.PlaylistEntity;
import com.company.youtube.enums.PlaylistStatus;
import com.company.youtube.mapper.PlaylistFullInfo;
import com.company.youtube.mapper.PlaylistShortInfo;
import com.company.youtube.mapper.playlist.PlaylistShortInfoRepository;
import com.company.youtube.mapper.playlist.PlaylistInfoRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends PagingAndSortingRepository<PlaylistEntity, Integer> {

    Optional<PlaylistEntity> findByName(String name);

    Optional<PlaylistEntity> findByIdAndVisibleTrue(Integer id);

    @Modifying
    @Transactional
    @Query("update  PlaylistEntity p set p.orderNum=?1 where p.id=?2")
    void updateOerderNum(Integer id, Integer pId);

    @Modifying
    @Transactional
    @Query("update  PlaylistEntity p set p.name=?1, p.description=?2 where p.id=?3")
    void updateNameAndDescription(String name, String description, Integer id);

    @Modifying
    @Transactional
    @Query("update  PlaylistEntity p set p.status=?1  where p.id=?2")
    void changeStatus(PlaylistStatus status, Integer id);

    @Modifying
    @Transactional
    @Query("update  PlaylistEntity p set p.visible=FALSE  where p.id=?1")
    void updateVisible(Integer id);

    @Query(value = "SELECT pl.id as id, pl.name as name, pl.description as description, pl.status as status, pl.order_num as orderNum,  " +
            " ch.id as channelId, ch.name as channelName, ch.photo_id as channelPhotoId,  " +
            " pr.id as profileId, pr.name as profileName, pr.surname as profileSurname, pr.photo_id as profilePhotoId " +
            " FROM playlist as pl " +
            " inner join channel as ch on ch.id =pl.channel_id " +
            " inner join profile as pr on pr.id=ch.profile_id " +
            " Where pr.id =:id",
            nativeQuery = true)
    List<PlaylistInfoRepository> getPlaylistUserId(@Param("id") Integer id);

    @Query(value = "SELECT p.id as playlistId, p.name as playListName, p.created_date as playListCreatedDate, " +
            "      c.id as channleId, c.name as channelName, " +
            " (select count(*) from playlist_video  as pv where pv.playlist_id = p.id ) as countVideo " +
            "     from  playlist as p " +
            " inner JOIN channel as c on p.channel_id = c.id " +
            "     where c.profile_id =:profileId " +
            "     and c.visible = true and p.visible = true ", nativeQuery = true)
    List<PlaylistShortInfo> getProfilePlayLists(@Param("profileId") Integer profileId);

    @Query(value = "SELECT p.id as playlistId, p.name as playListName, p.created_date as playListCreatedDate, " +
            "      c.id as channleId, c.name as channelName, " +
            " (select count(*) from playlist_video  as pv where pv.playlist_id = p.id ) as countVideo " +
            "     from  playlist as p " +
            " inner JOIN channel as c on p.channel_id = c.id " +
            "     where c.id = :id " +
            "     and c.visible = true and p.visible = true ", nativeQuery = true)
    List<PlaylistShortInfo> playListByChannelKey(@Param("id") String id);

    @Query(value = "SELECT p.id as playlistId, p.name as playlistName, "  +
            "    p.created_date as playlistCreatedDate, "  +
            "    c.id as channelId, c.name as channelName,  " +
            "        (select count(*) from playlist_video  as pv where pv.playlist_id = p.id ) as countVideo,  " +
            "        (select cast(count(*) as int)  " +
            "               from profile_watch_video as  pwv  " +
            "                inner join playlist_video as pv on pv.video_id = pwv.video_id  " +
            "                where pv.playlist_id =:id ) as totalWatchedCount " +
            "             from  playlist as p  " +
            "             inner join channel c  " +
            "             on c.id = p.channel_id  " +
            "             Where p.id = :id  " +
            "             and p.visible = true",nativeQuery = true)
    Optional<PlaylistShortInfo> getById(@Param("id") Integer playlistId);




}

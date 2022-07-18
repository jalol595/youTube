package com.company.youtube.repository;

import com.company.youtube.entity.CommentEntity;
import com.company.youtube.entity.VideoEntity;
import com.company.youtube.mapper.comment.CommentResponseRepository;
import com.company.youtube.mapper.comment.CommentShortInfoRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends PagingAndSortingRepository<CommentEntity, Integer> {

    Optional<CommentEntity> findByIdAndVisibleTrue(Integer integer);

    @Modifying
    @Transactional
    @Query("update  CommentEntity c set c.content=?1 where c.id=?2")
    void updateContent(String content, Integer id);

    @Query(value = "  select  c.id as id, c.content as content, c.like_count as likeCount, c.dis_like_count as dislikeCount, " +
            "  v.id as videoId,  v.preview_attach_id as previewAttachId, v.title as videoTitle " +
            "  FROM comment as c " +
            "  inner join profile as p on p.id = c.profile_id " +
            "  inner join video as v on v.id = c.video_id " +
            "  where p.id =:id ",
            nativeQuery = true)
    List<CommentResponseRepository> listByProfileId1(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("update  CommentEntity c set c.visible=false where c.id=?1")
    void delete(Integer id);


    @Query(value = "  SELECT  c.id as id, c.content as content, c.like_count as likeCount, c.dis_like_count as dislikeCount," +
            "  p.id as profileId, p.name as profileName, p.surname as profileSurname, p.photo_id as profilePhotoId" +
            "  FROM comment as c " +
            "  inner join profile as p on p.id=c.profile_id " +
            "  inner join video as v on v.id = c.video_id " +
            "  where v.id = :id ",
            nativeQuery = true)
    List<CommentShortInfoRepository> listByvideoId(@Param("id") String id);


    @Query(value = "  select  c.id as id, c.content as content, c.like_count as likeCount, c.dis_like_count as dislikeCount," +
            "  p.id as profileId, p.name as profileName, p.surname as profileSurname, p.photo_id as profilePhotoId" +
            "  FROM comment as c " +
            "  inner join profile as p on p.id=c.profile_id " +
            "  inner join video as v on v.id = c.video_id" +
            "  where c.reply_id = :id ",
            nativeQuery = true)
    List<CommentShortInfoRepository> commentRepliedCommentByCommentId(@Param("id") Integer id);

    Optional<CommentEntity> findByVideoAndContent(VideoEntity video, String content);
}

package com.company.youtube.repository;

import com.company.youtube.entity.CommentEntity;
import com.company.youtube.entity.CommentLikeEntity;
import com.company.youtube.entity.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity, Integer> {

    Optional<CommentLikeEntity> findByCommentAndProfile(CommentEntity comment, ProfileEntity profile);

    @Query("FROM CommentLikeEntity c where  c.comment.id=:commentId and c.profile.id =:profileId")
    Optional<CommentLikeEntity> findExists(Integer commentId, Integer profileId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CommentLikeEntity c where  c.comment.id=:commentId and c.profile.id =:profileId")
    void delete(Integer commentId, Integer profileId);
//    id,profile_id,comment_id,created_date,type(Like,Dislike)

   /* @Query("SELECT new CommentLikeEntity (c.id, c.profile, c.createdDate,c.likeStatus) " +
            " from CommentLikeEntity as c " +
            " inner join a.article as art " +
            " inner join a.types as t " +
            " Where t.key =:typeKey and art.visible = true and art.status = 'PUBLISHED' and art.id not in (:id)" +
            " order by art.publishDate ")
    List<CommentLikeEntity> findLast4TypeNotIn(@Param("typeKey") String typeKey, String id, Pageable pageable);



    @Query("SELECT new ArticleEntity(art.id,art.title,art.description,art.publishDate) " +
            " from ArticleTypeEntity as a " +
            " inner join a.article as art " +
            " inner join a.types as t " +
            " Where t.key =:typeKey and art.visible = true and art.status = 'PUBLISHED' and art.id not in (:id)" +
            " order by art.publishDate ")
    Page<ArticleEntity> findLast4TypeNotIn(@Param("typeKey") String typeKey, String id, Pageable pageable);
*/

}

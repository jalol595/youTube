package com.company.youtube.mapper.comment;

import java.time.LocalDateTime;

public interface CommentResponseRepository {

//        id,content,created_date,like_count,dislike_count, video(id,name,preview_attach_id,title,duration)

    Integer getId();
    String getContent();
    LocalDateTime getCreatedDate();
    Integer getLikeCount();
    Integer getDisLikeCount();

    String getVideoId();
    String getVideoTitle();
    String getPreviewAttachId();

}

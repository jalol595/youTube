package com.company.youtube.mapper.comment;

import java.time.LocalDateTime;

public interface CommentShortInfoRepository {



    Integer getId();
    String getContent();
    LocalDateTime getCreatedDate();
    Integer getLikeCount();
    Integer getDisLikeCount();

    Integer getProfileId();
    String getProfileName();
    String getProfileSurname();
    String getProfilePhotoId();

}

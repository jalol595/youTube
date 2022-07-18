package com.company.youtube.mapper.video;

import com.company.youtube.enums.VideoLikeStatus;

import java.time.LocalDateTime;

public interface VideoFullInfo {
    String getVideoId();
    String getVideoName();
    String getVideoDescription();

    LocalDateTime getCreatedDate();

    String getPreviewId();
    String getAttachId();

    Integer getViewCount();
    Integer getSharedCount();

    String getChannelId();
    String getChannelName();
    String getChannelPhotoId();

    Integer getCategoryId();
    String getCategoryName();

    Integer getVideoLikeCount();
    Integer getVideoDislikeCount();
    VideoLikeStatus getIsUserLiked();
}

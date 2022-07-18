package com.company.youtube.mapper;

public interface VideoLikeInfoRepository {

    Integer getId();

    String getVideoId();
    String getVideoTitle();
    String getVideoPreview();

    String getchannelId();
    String getchannelName();

}

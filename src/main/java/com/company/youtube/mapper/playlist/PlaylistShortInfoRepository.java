package com.company.youtube.mapper.playlist;

import java.time.LocalDateTime;

public interface PlaylistShortInfoRepository {

    Integer getId();
    String getName();
    LocalDateTime getCreatedDate();

    String getChannelId();
    String getChannelName();

    String getVideoId();
    String getVideoTitle();
    Integer getVideoViewCount();

}

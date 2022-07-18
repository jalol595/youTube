package com.company.youtube.mapper.playlistVideo;

import java.time.LocalDateTime;

public interface PlaylistVideoInfoRepository {


    Integer getId();
    Integer getOderNum();
    LocalDateTime getCreatedDate();

    String getVideoId();
    String getVideoPreview();
    String getVideoPreviewUrl();
    String getVideoTitle();


    String getchannelId();
    String getchannelName();


}

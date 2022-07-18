package com.company.youtube.mapper.video;

import com.company.youtube.dto.attach.AttachDTO;
import com.company.youtube.dto.channel.ChannelDTO;

import java.time.LocalDateTime;

public interface VideoShortInfoRepository {

     String getId();
     String getTitle();
     Integer getViewCount();
     String getPreview();
     LocalDateTime getPublishedDate();
     String getchannelId();
     String getchannelName();
     String getchannelPhoto();

}

package com.company.youtube.dto.videoLike;

import com.company.youtube.dto.channel.ChannelDTO;
import com.company.youtube.dto.video.VideoDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoLikeInfoDTO {
//         id,video(id,name,key,channel(id,name),duration),preview_attach(id,url)

    private Integer id;

    private VideoDTO videoDTO;
    private ChannelDTO channelDTO;



}

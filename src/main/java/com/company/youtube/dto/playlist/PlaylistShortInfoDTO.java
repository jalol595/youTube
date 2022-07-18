package com.company.youtube.dto.playlist;

import com.company.youtube.dto.channel.ChannelDTO;
import com.company.youtube.dto.profile.ProfileDTO;
import com.company.youtube.dto.video.VideoDTO;
import com.company.youtube.enums.PlaylistStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistShortInfoDTO {

//     PlayListShortInfo
//            id, name,created_date,channel(id,name),video_count,video_list[{id,name,key,duration}]


    private Integer id;
    private String name;
    private LocalDateTime createdDate;

    private ChannelDTO channel;
    private VideoDTO video;

    private Integer videoCount;
    private Integer totalViewCount;

private PlaylistDTO playlist;

private List<VideoDTO> videoList;

}

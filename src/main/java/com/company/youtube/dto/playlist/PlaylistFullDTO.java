package com.company.youtube.dto.playlist;


import com.company.youtube.dto.video.VideoShortInfo;
import com.company.youtube.dto.video.VideoShortInfoDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaylistFullDTO {

    private String playlistName;
    private String playlistId;
    private Integer playlistViewCount;
    private List<VideoShortInfo> videoShortInfoDTOS;

}

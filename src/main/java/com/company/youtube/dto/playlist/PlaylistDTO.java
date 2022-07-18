package com.company.youtube.dto.playlist;

import com.company.youtube.dto.channel.ChannelDTO;
import com.company.youtube.dto.video.VideoDTO;
import com.company.youtube.enums.PlaylistStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistDTO {

    private Integer id;
    private String title;
    private String decription;
    private ChannelDTO channel;
    private Integer order;
    private PlaylistStatus status;
    private Boolean visible;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String attachUrl;
    private Integer videoCount;

    private List<VideoDTO> videoList;

    private List<VideoPlaylistInfoDTO> videoPlaylistInfo;
}

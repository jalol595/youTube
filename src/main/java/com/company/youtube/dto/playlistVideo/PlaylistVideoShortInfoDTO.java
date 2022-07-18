package com.company.youtube.dto.playlistVideo;

import com.company.youtube.dto.channel.ChannelDTO;

import com.company.youtube.dto.video.VideoDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistVideoShortInfoDTO {

    Integer id;

    @NotNull
    private ChannelDTO channelDTO;

    @NotNull
    private VideoDTO video;

    @NotNull
    private Integer orderNum;

    private LocalDateTime createdDate;

}

package com.company.youtube.dto.playlistVideo;


import com.company.youtube.dto.playlist.PlaylistDTO;
import com.company.youtube.dto.video.VideoDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponsePlaylistVideoDTO {

    @NotNull
    private PlaylistDTO playlist;

    @NotNull
    private VideoDTO video;

    @NotNull
    private Integer orderNum;
}

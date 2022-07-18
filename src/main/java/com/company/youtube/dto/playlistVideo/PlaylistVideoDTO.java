package com.company.youtube.dto.playlistVideo;


import com.company.youtube.dto.video.VideoDTO;
import com.company.youtube.entity.PlaylistEntity;
import com.company.youtube.entity.VideoEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistVideoDTO {

    @NotNull
    private Integer playlist;

    @NotNull
    private String video;


    @NotNull
    private Integer newPlaylist;

    @NotNull
    private String newVideo;

    private Integer orderNume;


}

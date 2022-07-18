package com.company.youtube.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
@Getter
@Setter
public class VideoTagDTO {

    @NotNull
    private Integer tadId;
    @NotNull
    private String videoId;

}

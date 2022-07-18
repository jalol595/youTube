package com.company.youtube.dto.video;

import com.company.youtube.dto.attach.AttachDTO;
import com.company.youtube.dto.channel.ChannelDTO;
import com.company.youtube.enums.TypeVideo;
import com.company.youtube.enums.VideoStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateVideoDTO {



    @NotBlank
    @Size(min = 3)
    private String title;


    @NotNull
    private TypeVideo type;

    @NotBlank
    @Size(min = 5)
    private String description;

    @NotNull
    private ChannelDTO channel;





}

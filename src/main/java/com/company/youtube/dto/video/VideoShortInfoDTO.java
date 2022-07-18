package com.company.youtube.dto.video;

import com.company.youtube.dto.attach.AttachDTO;
import com.company.youtube.dto.channel.ChannelDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoShortInfoDTO {

    private String id;
    private String title;
    private Integer viewCount;
    private AttachDTO preview;
    private String previewUrl;
    private LocalDateTime publishedDate;
    private ChannelDTO channel;


}

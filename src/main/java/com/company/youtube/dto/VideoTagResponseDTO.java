package com.company.youtube.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class VideoTagResponseDTO {
//     (id,video_id,tag(id,name),created_date)
    private Integer id;
    private String videoId;
    private TagDTO tag;
    private LocalDateTime createdDate;
}

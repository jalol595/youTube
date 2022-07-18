package com.company.youtube.dto.video;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoShortInfo {



    private String videoId;
    private String videoName;
    private String videoReviewOpenUrl;
    private Integer viewCount;
}

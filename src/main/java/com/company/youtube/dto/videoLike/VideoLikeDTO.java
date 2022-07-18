package com.company.youtube.dto.videoLike;

import com.company.youtube.enums.VideoLikeStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoLikeDTO {
    private VideoLikeStatus videoLikeStatus;
    private String videoId;

}

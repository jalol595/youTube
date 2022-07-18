package com.company.youtube.dto.comment;

import com.company.youtube.dto.video.VideoDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
public class CommentResponseDTO {
    //        id,content,created_date,like_count,dislike_count, video(id,name,preview_attach_id,title,duration)

    private Integer id;
    private String content;
    private LocalDateTime createdDate;
    private Integer likeCount;
    private Integer disLikeCount;

    private VideoDTO video;

}

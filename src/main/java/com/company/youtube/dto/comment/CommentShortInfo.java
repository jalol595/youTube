package com.company.youtube.dto.comment;

import com.company.youtube.dto.profile.ProfileDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Getter
@Setter
public class CommentShortInfo {

    //    id,content,created_date,like_count,dislike_count,
//    profile(id,name,surname,photo(id,url))

    private String content;
    private Integer likeCount;
    private Integer disLikeCount;
    private LocalDateTime createdDate;
    private ProfileDTO profile;

}

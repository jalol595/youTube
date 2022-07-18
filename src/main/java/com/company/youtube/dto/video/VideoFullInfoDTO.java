package com.company.youtube.dto.video;

import com.company.youtube.dto.CategoryDTO;
import com.company.youtube.dto.TagDTO;
import com.company.youtube.dto.channel.ChannelDTO;
import com.company.youtube.dto.videoLike.VideoLikeDTO;
import com.company.youtube.dto.attach.AttachDTO;
import com.company.youtube.enums.VideoLikeStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoFullInfoDTO {

    //        VideFullInfo (id,key,title,description,
//                preview_attach(id,url),attach(id,url,duration),
//                category(id,name),tagList(id,name),
//                published_date, channel(id,name,photo(url)),
//                view_count,shared_count,Like(like_count,dislike_count,
//                isUserLiked,IsUserDisliked),duration)
    private String id;
    private String title;
    private String description;
    private LocalDateTime publishedDate;
    private Integer viewCount;
    private Integer sharedCount;
    private Integer likeCount;
    private Integer dislikeCount;
    private VideoLikeStatus isUserLiked;

    private AttachDTO preview;
    private String previewUrl;

    private AttachDTO photo;

    private CategoryDTO category;
    private TagDTO tag;

    private VideoLikeDTO like;

    private VideoDTO video;

    private ChannelDTO channel;

    private AttachDTO attach;
    private List<TagDTO> tagList;

}

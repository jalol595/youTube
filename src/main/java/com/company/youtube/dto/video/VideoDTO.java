package com.company.youtube.dto.video;

import com.company.youtube.dto.CategoryDTO;
import com.company.youtube.dto.TagDTO;
import com.company.youtube.dto.attach.AttachDTO;
import com.company.youtube.dto.channel.ChannelDTO;
import com.company.youtube.dto.profile.ProfileDTO;
import com.company.youtube.entity.AttachEntity;
import com.company.youtube.entity.ChannelEntity;
import com.company.youtube.entity.ProfileEntity;
import com.company.youtube.enums.TypeVideo;
import com.company.youtube.enums.VideoStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoDTO {


    private String id;

    private AttachDTO preview;

    private String title;

    private AttachDTO photo;

    private LocalDateTime publishedDate;

    private VideoStatus status;

    private TypeVideo type;

    private Integer viewCount=0;

    private Integer sharedCount=0;

    private String description;

    private Integer likeCount=0;

    private Integer disLikeCount=0;

    private ChannelDTO channel;

    private AttachDTO baner;

    private ProfileDTO profile;

    private List<CategoryDTO> categoryList;
    private List<TagDTO> tagList;

    private String preUrl;

}

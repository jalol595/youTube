package com.company.youtube.dto.comment;

import com.company.youtube.entity.CommentEntity;
import com.company.youtube.entity.ProfileEntity;
import com.company.youtube.entity.VideoEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {

    @NotNull
    private String videoId;

    @NotNull
    private String content;


    private Integer profileId;

    private Integer likeCount;

    private Integer disLikeCount;

    private LocalDateTime createdDate;

}

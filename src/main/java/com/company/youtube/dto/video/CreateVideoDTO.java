package com.company.youtube.dto.video;

import com.company.youtube.dto.CategoryDTO;
import com.company.youtube.dto.TagDTO;
import com.company.youtube.dto.attach.AttachDTO;
import com.company.youtube.dto.channel.ChannelDTO;
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
public class CreateVideoDTO {


    private String id;

    @NotNull
    private AttachDTO preview;

    @NotBlank
    @Size(min = 3)
    private String title;

    private AttachDTO photo;

    @NotNull
    private VideoStatus status;

    @NotNull
    private TypeVideo type;

    @NotBlank
    @Size(min = 5)
    private String description;

    @NotNull
    private ChannelDTO channel;

    private AttachDTO baner;


    private List<Integer> categoryList;
    private List<String> tagList;

}

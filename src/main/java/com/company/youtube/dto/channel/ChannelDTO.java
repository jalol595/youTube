package com.company.youtube.dto.channel;

import com.company.youtube.dto.attach.AttachDTO;
import com.company.youtube.dto.profile.ProfileDTO;
import com.company.youtube.entity.AttachEntity;
import com.company.youtube.entity.ProfileEntity;
import com.company.youtube.enums.ChannelStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelDTO {

    private String id;

    @NotBlank
    @Size(min = 4)
    private String name;

    @NotNull
    private String description;


    private AttachDTO photoId;


    private AttachDTO banerId;


    private Integer profileId;


    private String websiteUrl;


    private String telegramUrl;


    private String instagramUrl;

    private String url;


    private LocalDateTime createdDate;

    public ChannelDTO() {
    }

    public ChannelDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ChannelDTO(String id) {
        this.id = id;
    }
}

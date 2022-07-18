package com.company.youtube.dto.channel;

import com.company.youtube.dto.attach.AttachDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateChannelDTO {

    private String id;

    @NotBlank
    @Size(min = 4)
    private String name;

    @NotNull
    private String description;


    private Integer profileId;


    private String websiteUrl;


    private String telegramUrl;


    private String instagramUrl;

}

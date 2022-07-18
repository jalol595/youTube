package com.company.youtube.dto.playlist;

import com.company.youtube.dto.channel.ChannelDTO;
import com.company.youtube.dto.profile.ProfileDTO;
import com.company.youtube.enums.PlaylistStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistInfoDTO {

//      id,name,description,status(private,public),order_num,
//            channel(id,name,photo(id,url), profile(id,name,surname,photo(id,url)))

    private Integer id;
    private String name;
    private String description;
    private PlaylistStatus status;
    private Integer orderNum;

    private ChannelDTO channel;

    private ProfileDTO profile;
}

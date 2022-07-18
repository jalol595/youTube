package com.company.youtube.dto.channel;

import com.company.youtube.dto.attach.AttachDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateChannelPhotoDTO {

    private AttachDTO photoId;

    private AttachDTO banerId;

}

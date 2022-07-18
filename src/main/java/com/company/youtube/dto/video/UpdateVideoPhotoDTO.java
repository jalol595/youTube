package com.company.youtube.dto.video;

import com.company.youtube.dto.attach.AttachDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateVideoPhotoDTO {

    private AttachDTO photoId;

    private AttachDTO banerId;

}

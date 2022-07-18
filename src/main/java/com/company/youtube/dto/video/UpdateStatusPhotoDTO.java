package com.company.youtube.dto.video;

import com.company.youtube.enums.VideoStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateStatusPhotoDTO {

    @NotNull
    private VideoStatus status;

}

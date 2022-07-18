package com.company.youtube.dto.attach;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateAttachDTO {

    private String photoId;

}

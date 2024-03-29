package com.company.youtube.dto.attach;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachDTO {

    private String id;

    private  String orginalName;

    private String extensional;

    private  Long size;

    private String path;

    private LocalDateTime createdDate;

    private String url;

    public AttachDTO() {
    }

    public AttachDTO(String id) {
        this.id = id;
    }
}

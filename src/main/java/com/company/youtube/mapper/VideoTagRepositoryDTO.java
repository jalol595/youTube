package com.company.youtube.mapper;

import com.company.youtube.dto.TagDTO;

import java.time.LocalDateTime;

public interface VideoTagRepositoryDTO {


    Integer getId();
    String getVideoId();
    Integer getTagId();
    String getTagName();
    LocalDateTime getCreatedDate();

}

package com.company.youtube.repository;

import com.company.youtube.entity.VideoCategoryEntity;
import com.company.youtube.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoCategoryRepository extends JpaRepository<VideoCategoryEntity, Integer> {
    List<VideoCategoryEntity> findAllByVideo(VideoEntity video);

}

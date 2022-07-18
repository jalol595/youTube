package com.company.youtube.service;

import com.company.youtube.dto.CategoryDTO;
import com.company.youtube.entity.CategoryEntity;
import com.company.youtube.entity.VideoCategoryEntity;
import com.company.youtube.entity.VideoEntity;
import com.company.youtube.entity.VideoTagEntity;
import com.company.youtube.repository.VideoCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoCategoryService {

    @Autowired
    private VideoCategoryRepository videoCategoryRepository;

    @Autowired
    private CategoryService categoryService;

    public void create(VideoEntity video, List<Integer> categoryList) {

        for (Integer categoryId : categoryList) {
            VideoCategoryEntity videoCategory = new VideoCategoryEntity();

            videoCategory.setVideo(video);
            videoCategory.setCategory(new CategoryEntity(categoryId));
            videoCategoryRepository.save(videoCategory);
        }

    }

    public List<CategoryDTO> getList(VideoEntity videoEntity) {

        List<VideoCategoryEntity> list = videoCategoryRepository.findAllByVideo(videoEntity);

        List<CategoryDTO> tagDTOList = new ArrayList<>();
        list.forEach(categoryEntity -> {
            CategoryDTO dto = categoryService.getCategoryDTO(categoryEntity.getCategory());
            tagDTOList.add(dto);
        });

        return tagDTOList;
    }

}

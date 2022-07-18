package com.company.youtube.service;

import com.company.youtube.dto.CategoryDTO;
import com.company.youtube.dto.response.ResposeDTO;
import com.company.youtube.entity.CategoryEntity;
import com.company.youtube.entity.VideoCategoryEntity;
import com.company.youtube.entity.VideoEntity;
import com.company.youtube.exseptions.AlreadyExist;
import com.company.youtube.exseptions.BadRequestException;
import com.company.youtube.exseptions.ItemNotFoundEseption;
import com.company.youtube.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO dto) {

        if (categoryRepository.existsByNameAndVisible(dto.getName(), true)) {
            throw new AlreadyExist("Alredy exist");
        }

        CategoryEntity category = new CategoryEntity();
        category.setName(dto.getName());
        category.setCreatedDate(LocalDateTime.now());
        categoryRepository.save(category);

        CategoryDTO response = new CategoryDTO();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setCreatedDate(category.getCreatedDate());
        return response;
    }

    public CategoryDTO update(Integer id, CategoryDTO dto) {

        Optional<CategoryEntity> entity = categoryRepository.findById(id);
        if (entity.isEmpty()){
            throw new ItemNotFoundEseption("not found");
        }else if (entity.get().getName().equals(dto.getName())){
            throw new BadRequestException("you are axmoq qilayapsanmi???");
        }

        CategoryEntity category = entity.get();
        category.setName(dto.getName());
        categoryRepository.save(category);
        
        return new CategoryDTO(category.getId(), category.getName(), category.getCreatedDate());
    }

    public ResposeDTO delete(Integer id) {
        if (!categoryRepository.existsById(id)){
            throw new ItemNotFoundEseption("not found");
        }

        categoryRepository.updateStatusById(id);

        return new ResposeDTO("sucsess", true);
    }

    public List<CategoryDTO> getList() {

            Iterable<CategoryEntity> all = categoryRepository.findAllByVisible(true);
            List<CategoryDTO> dtoList = new LinkedList<>();

            all.forEach(category -> {
                CategoryDTO dto = new CategoryDTO();
                dto.setId(category.getId());
                dto.setName(category.getName());
                dto.setCreatedDate(category.getCreatedDate());
                dtoList.add(dto);
            });
            return dtoList;
        }

    public CategoryDTO getCategoryDTO(CategoryEntity category) {
        CategoryDTO response = new CategoryDTO();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setCreatedDate(category.getCreatedDate());
        return response;

    }
}

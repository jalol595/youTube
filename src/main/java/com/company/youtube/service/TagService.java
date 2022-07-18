package com.company.youtube.service;

import com.company.youtube.dto.CategoryDTO;
import com.company.youtube.dto.TagDTO;
import com.company.youtube.dto.response.ResposeDTO;
import com.company.youtube.entity.CategoryEntity;
import com.company.youtube.entity.TagEntity;
import com.company.youtube.exseptions.AlreadyExist;
import com.company.youtube.exseptions.BadRequestException;
import com.company.youtube.exseptions.ItemNotFoundEseption;
import com.company.youtube.repository.CategoryRepository;
import com.company.youtube.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public TagDTO create(TagDTO dto) {

        if (tagRepository.existsByNameAndVisible(dto.getName(), true)) {
            throw new AlreadyExist("Alredy exist");
        }

        TagEntity category = new TagEntity();
        category.setName(dto.getName());
        category.setCreatedDate(LocalDateTime.now());
        tagRepository.save(category);

        TagDTO response = new TagDTO();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setCreatedDate(category.getCreatedDate());
        return response;
    }

    public TagDTO update(Integer id, TagDTO dto) {

        Optional<TagEntity> entity = tagRepository.findById(id);
        if (entity.isEmpty()){
            throw new ItemNotFoundEseption("not found");
        }else if (entity.get().getName().equals(dto.getName())){
            throw new BadRequestException("you are axmoq qilayapsanmi???");
        }

        TagEntity category = entity.get();
        category.setName(dto.getName());
        tagRepository.save(category);
        
        return new TagDTO(category.getId(), category.getName(), category.getCreatedDate());
    }

    public ResposeDTO delete(Integer id) {
        if (!tagRepository.existsById(id)){
            throw new ItemNotFoundEseption("not found");
        }

        tagRepository.updateStatusById(id);

        return new ResposeDTO("sucsess", true);
    }

    public List<TagDTO> getList() {

            Iterable<TagEntity> all = tagRepository.findAllByVisible(true);
            List<TagDTO> dtoList = new LinkedList<>();

            all.forEach(category -> {
                TagDTO dto = new TagDTO();
                dto.setId(category.getId());
                dto.setName(category.getName());
                dto.setCreatedDate(category.getCreatedDate());
                dtoList.add(dto);
            });
            return dtoList;
        }

    public TagEntity create(String name){
        TagEntity tag=new TagEntity();
        tag.setName(name);
        tagRepository.save(tag);
        return tag;
    }

    public TagEntity createIfNotExsists(String tagName){
        Optional<TagEntity> optional = tagRepository.findByName(tagName);
        if (optional.isEmpty()){
            return create(tagName);
        }
        return optional.get();
        //  return tagRepository.findByName(tagName).orElse(create(tagName));
    }

    public TagDTO getTagDTO(TagEntity entity) {

        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(entity.getId());
        tagDTO.setCreatedDate(entity.getCreatedDate());
        tagDTO.setName(entity.getName());
        return tagDTO;
    }

    public TagEntity getTag(Integer id) {
        return tagRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundEseption("tag not found");
        });
    }

    public List<TagDTO> getTagListByVideoId(String videoId) {
        List<TagEntity> list = tagRepository.listByVideoId(videoId);
        List<TagDTO> dtoList = new LinkedList<>();

        list.forEach(tagEntity -> {
            TagDTO dto = toDTO(tagEntity);

            dtoList.add(dto);
        });

        return dtoList;

    }

    private TagDTO toDTO(TagEntity entity) {
        TagDTO dto = new TagDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        return dto;
    }
}

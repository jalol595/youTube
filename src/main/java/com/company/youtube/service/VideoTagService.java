package com.company.youtube.service;

import com.company.youtube.config.CustomUserDetails;
import com.company.youtube.dto.TagDTO;
import com.company.youtube.dto.VideoTagDTO;
import com.company.youtube.dto.VideoTagResponseDTO;
import com.company.youtube.dto.response.ResposeDTO;
import com.company.youtube.dto.video.VideoDTO;
import com.company.youtube.entity.ProfileEntity;
import com.company.youtube.entity.TagEntity;
import com.company.youtube.entity.VideoEntity;
import com.company.youtube.entity.VideoTagEntity;
import com.company.youtube.exseptions.AlreadyExist;
import com.company.youtube.exseptions.BadRequestException;
import com.company.youtube.exseptions.ItemNotFoundEseption;
import com.company.youtube.mapper.VideoTagRepositoryDTO;
import com.company.youtube.repository.TagRepository;
import com.company.youtube.repository.VideoRepository;
import com.company.youtube.repository.VideoTagRepository;
import com.company.youtube.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class VideoTagService {

    @Autowired
    private TagService tagService;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoTagRepository videoTagRepository;


    public ResposeDTO createVideoTag(VideoTagDTO dto) {

        TagEntity tag = tagService.getTag(dto.getTadId());

        Optional<VideoEntity> video = videoRepository.findByIdAndVisibleTrue(dto.getVideoId());
        if (video.isEmpty()){
            throw new BadRequestException("not found");
        }

        Optional<VideoTagEntity> videoAndTag = videoTagRepository.findByVideoAndTag(new VideoEntity(dto.getVideoId()), new TagEntity(dto.getTadId()));
        if (videoAndTag.isPresent()){
            throw new AlreadyExist("Already exsists");
        }

        VideoEntity entity = video.get();

        if (!entity.getProfile().getId().equals(getProfile().getId())) {
            throw new BadRequestException("not permish");
        }

        VideoTagEntity videoTagEntity = new VideoTagEntity();
        videoTagEntity.setTag(tag);
        videoTagEntity.setVideo(entity);
        videoTagRepository.save(videoTagEntity);

        return new ResposeDTO("succsess", true);

    }

    public ResposeDTO delete(Integer id) {
        Optional<VideoTagEntity> videoTag = videoTagRepository.findById(id);
        if (videoTag.isEmpty()) {
            throw new BadRequestException("not found");
        } else if (!videoTag.get().getVideo().getProfile().getId().equals(getProfile().getId())) {
            throw new BadRequestException("not permish");
        }

        videoTagRepository.delete(id);
        return new ResposeDTO("succsess", true);

    }

    public void create(VideoEntity videoEntity, List<String> tagList) {
        for (String tagName : tagList) {
            TagEntity tagEntity = tagService.createIfNotExsists(tagName);

            VideoTagEntity videoTagEntity = new VideoTagEntity();
            videoTagEntity.setVideo(videoEntity);
            videoTagEntity.setTag(tagEntity);
            videoTagRepository.save(videoTagEntity);
        }


    }

    public List<TagDTO> getList(VideoEntity videoEntity) {

        List<VideoTagEntity> list = videoTagRepository.findAllByVideo(videoEntity);

        List<TagDTO> tagDTOList = new ArrayList<>();
        list.forEach(articleTagEntity -> {
            TagDTO tagDTO = tagService.getTagDTO(articleTagEntity.getTag());
            tagDTOList.add(tagDTO);
        });

        return tagDTOList;
    }

    public ProfileEntity getProfile() {
        CustomUserDetails user = SpringSecurityUtil.getCurrentUser();
        return user.getProfile();
    }

    public List<VideoTagResponseDTO> videoTagListByVideoId(String videoId) {
        List<VideoTagRepositoryDTO> repository = videoTagRepository.getByVideoId(videoId);
        List<VideoTagResponseDTO> dtoList = new LinkedList<>();

        repository.forEach(videoTagRepository -> {

            VideoTagResponseDTO videoTagResponseDTO = new VideoTagResponseDTO();
            videoTagResponseDTO.setId(videoTagRepository.getId());
            videoTagResponseDTO.setVideoId(videoTagRepository.getVideoId());

            TagDTO tagDTO=new TagDTO();
            tagDTO.setId(videoTagRepository.getTagId());
            tagDTO.setName(videoTagRepository.getTagName());

            videoTagResponseDTO.setTag(tagDTO);
            videoTagResponseDTO.setCreatedDate(videoTagRepository.getCreatedDate());
             dtoList.add(videoTagResponseDTO);
        });

        return dtoList;
    }
}

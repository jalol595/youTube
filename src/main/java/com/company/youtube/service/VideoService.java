package com.company.youtube.service;

import com.company.youtube.config.CustomUserDetails;
import com.company.youtube.dto.CategoryDTO;
import com.company.youtube.dto.TagDTO;
import com.company.youtube.dto.attach.AttachDTO;
import com.company.youtube.dto.channel.ChannelDTO;
import com.company.youtube.dto.playlist.VideoPlaylistInfoDTO;
import com.company.youtube.dto.profile.ProfileDTO;
import com.company.youtube.dto.response.ResposeDTO;
import com.company.youtube.dto.video.*;
import com.company.youtube.dto.videoLike.VideoLikeDTO;
import com.company.youtube.entity.*;
import com.company.youtube.enums.ProfileRole;
import com.company.youtube.enums.VideoStatus;
import com.company.youtube.exseptions.AlreadyExist;
import com.company.youtube.exseptions.BadRequestException;
import com.company.youtube.exseptions.ItemNotFoundEseption;
import com.company.youtube.mapper.video.VideoFullInfo;
import com.company.youtube.mapper.video.VideoShortInfoRepository;
import com.company.youtube.repository.VideoRepository;
import com.company.youtube.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private VideoCategoryService videoCategoryService;

    @Autowired
    private VideoTagService videoTagService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private AttachService attachService;

    @Value("${server.url}")
    private String serverUrl;


    public VideoDTO create(CreateVideoDTO dto) {

        Optional<VideoEntity> video =
                videoRepository.findByTitleAndPreviewAndVisibleTrue(dto.getTitle(), new AttachEntity(dto.getPreview().getId()));

        if (video.isPresent()) {
            throw new AlreadyExist("alredy exsists video");
        }

        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setTitle(dto.getTitle());
        videoEntity.setDescription(dto.getDescription());
        videoEntity.setPreview(new AttachEntity(dto.getPreview().getId()));
        videoEntity.setChannel(new ChannelEntity(channelService.get(dto.getChannel().getId()).getId()));

        if (dto.getPhoto() == null) {
            videoEntity.setPhoto(null);
        } else {
            videoEntity.setPhoto(new AttachEntity(dto.getPhoto().getId()));
        }

        if (dto.getBaner() == null) {
            videoEntity.setBaner(null);
        } else {
            videoEntity.setBaner(new AttachEntity(dto.getBaner().getId()));
        }


/*        videoEntity.setPhoto(new AttachEntity(dto.getPhoto().getId()));

        videoEntity.setBaner(new AttachEntity(dto.getBaner().getId()));*/

        videoEntity.setStatus(dto.getStatus());
        videoEntity.setType(dto.getType());
        videoEntity.setProfile(new ProfileEntity(getProfile().getId()));
        videoRepository.save(videoEntity);

        videoCategoryService.create(videoEntity, dto.getCategoryList());
        videoTagService.create(videoEntity, dto.getTagList());

        VideoDTO response = new VideoDTO();
        response.setId(videoEntity.getId());
        response.setTitle(videoEntity.getTitle());
        response.setDescription(videoEntity.getDescription());
        response.setPreview(new AttachDTO(videoEntity.getPreview().getId()));

        response.setChannel(new ChannelDTO(videoEntity.getChannel().getId()));

        if (videoEntity.getPhoto() == null) {
            response.setPhoto(null);
        } else {
            response.setPhoto(new AttachDTO(videoEntity.getPhoto().getId()));
        }

        if (videoEntity.getBaner() == null) {
            response.setBaner(null);
        } else {
            response.setBaner(new AttachDTO(videoEntity.getBaner().getId()));
        }

        response.setPhoto(new AttachDTO(videoEntity.getPhoto().getId()));

        response.setBaner(new AttachDTO(videoEntity.getBaner().getId()));

        response.setStatus(videoEntity.getStatus());
        response.setType(videoEntity.getType());
        response.setProfile(new ProfileDTO(videoEntity.getProfile().getId()));
        response.setDisLikeCount(videoEntity.getDisLikeCount());
        response.setLikeCount(videoEntity.getLikeCount());
        response.setViewCount(videoEntity.getViewCount());
        response.setSharedCount(videoEntity.getSharedCount());
        response.setTagList(videoTagService.getList(videoEntity));
        response.setCategoryList(videoCategoryService.getList(videoEntity));

        return response;
    }

    public ResposeDTO update(String id, UpdateVideoDTO dto) {
        VideoEntity videoEntity = checkPerMit(id);
        videoEntity.setTitle(dto.getTitle());
        videoEntity.setDescription(dto.getDescription());
        videoEntity.setType(dto.getType());
        videoEntity.setChannel(channelService.get(dto.getChannel().getId()));
        videoRepository.updateDeatil(dto.getTitle(), dto.getDescription(), dto.getType(), dto.getChannel().getId(), id);
        return new ResposeDTO("succsess", true);
    }

    public ResposeDTO updatePhoto(String id, UpdateVideoPhotoDTO dto) {
        return updatePhotoChannel(id, dto);
    }

    public ResposeDTO updateBaner(String id, UpdateVideoPhotoDTO dto) {
        return updatePhotoChannelBaner(id, dto);
    }

    public ResposeDTO changeStatus(String id, UpdateStatusPhotoDTO dto) {
        VideoEntity videoEntity = checkPerMit(id);
        videoEntity.setStatus(dto.getStatus());
        videoRepository.changeStatus(dto.getStatus(), videoEntity.getId());
        return new ResposeDTO("succsess", true);
    }

    public void increaseViewCountbyKey(String id) {
        Optional<VideoEntity> optional = videoRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundEseption("video Not Found");
        }

        VideoEntity entity = optional.get();

        videoRepository.updtecount(id, entity.getViewCount() + 1);

    }

    public List<VideoShortInfoDTO> getVideoPaginationbyCategoryId(Integer categoryId) {

        Pageable pageable = PageRequest.of(0, 2);
        Page<VideoShortInfoRepository> list = videoRepository.getVideoPaginationbyCategoryId(categoryId, pageable);
        List<VideoShortInfoDTO> dtoList = new LinkedList<>();
        list.forEach(infoRepository -> {
            dtoList.add(getVideoShorInfoDTO(infoRepository));
        });

        return dtoList;

    }

    public List<VideoShortInfoDTO> videoByTitle(String title) {

        List<VideoShortInfoRepository> bytitle = videoRepository.getBytitle(title);
        List<VideoShortInfoDTO> dtoList = new LinkedList<>();

        bytitle.forEach(infoRepository -> {
            dtoList.add(getVideoShorInfoDTO(infoRepository));
        });

        return dtoList;
    }

    public List<VideoShortInfoDTO> videoByTagIdWithPagination(Integer id) {

        Pageable pageable = PageRequest.of(0, 4);
        Page<VideoShortInfoRepository> list = videoRepository.videoByTagIdWithPagination(id, pageable);
        List<VideoShortInfoDTO> dtoList = new LinkedList<>();
        list.forEach(infoRepository -> {
            dtoList.add(getVideoShorInfoDTO(infoRepository));
        });

        return dtoList;

    }

    public List<VideoPlaylistInfoDTO> getPlaylistFirstTwoVideo(Integer playlistId) {
        return videoRepository.getVideoFoPlayList(playlistId).stream().map(info -> {
            VideoPlaylistInfoDTO dto = new VideoPlaylistInfoDTO();
            dto.setPlaylistVideoId(info.getPlayListVideoId());
            dto.setVideoId(info.getVideoId());
            dto.setVideoName(info.getVideoName());
            return dto;
        }).collect(Collectors.toList());
    }

    public ProfileEntity getProfile() {
        CustomUserDetails user = SpringSecurityUtil.getCurrentUser();
        return user.getProfile();
    }

    public boolean get(String id) {

        Optional<VideoEntity> entity = videoRepository.findById(id);
        if (entity.get().getVisible().equals(Boolean.TRUE)) {
            return true;
        }
        return false;
    }

    private ResposeDTO updatePhotoChannel(String id, UpdateVideoPhotoDTO dto) {
        Optional<VideoEntity> entity = videoRepository.findByIdAndVisibleTrue(id);

        if (entity.isEmpty()) {
            throw new ItemNotFoundEseption("not found");
        } else if (!entity.get().getProfile().getId().equals(getProfile().getId())) {
            throw new BadRequestException("not permish");
        } else if (entity.get().getPhoto() == null && dto.getPhotoId() == null) {
            throw new BadRequestException("rasm yoq edi");
        }

        VideoEntity video = entity.get();

        if (video.getPhoto() != null && dto.getPhotoId() == null) {
            videoRepository.deletePhoto(null, video.getId());
            attachService.delete(video.getPhoto().getId());
        } else if (video.getPhoto() != null && dto.getPhotoId() == null
                && video.getPhoto() != (new AttachEntity(dto.getPhotoId().getId()))) {
            videoRepository.updatePhoto(new AttachEntity(dto.getPhotoId().getId()), video.getId());
            attachService.delete(video.getPhoto().getId());
        } else if (video.getPhoto() == null && dto.getPhotoId() != null) {
            video.setPhoto(new AttachEntity(dto.getPhotoId().getId()));
            videoRepository.updatePhoto(new AttachEntity(dto.getPhotoId().getId()), video.getId());
        }

        return new ResposeDTO("succsess", true);

    }

    private ResposeDTO updatePhotoChannelBaner(String id, UpdateVideoPhotoDTO dto) {

        Optional<VideoEntity> entity = videoRepository.findByIdAndVisibleTrue(id);

        if (entity.isEmpty()) {
            throw new ItemNotFoundEseption("not found");
        } else if (!entity.get().getProfile().getId().equals(getProfile().getId())) {
            throw new BadRequestException("not permish");
        } else if (entity.get().getBaner() == null && dto.getBanerId() == null) {
            throw new BadRequestException("baner yoq edi");
        }

        VideoEntity video = entity.get();

        if (video.getBaner() != null && dto.getBanerId() == null) {
            videoRepository.deletePhotoBaner(null, video.getId());
            attachService.delete(video.getBaner().getId());
        } else if (video.getBaner() != null && dto.getBanerId().getId() != null
                && video.getPhoto() != (new AttachEntity(dto.getBanerId().getId()))) {
            videoRepository.updatePhotoBaner(new AttachEntity(dto.getBanerId().getId()), video.getId());
            attachService.delete(video.getBaner().getId());
        } else if (video.getBaner() == null && dto.getBanerId().getId() != null) {
            video.setBaner(new AttachEntity(dto.getBanerId().getId()));
            videoRepository.updatePhotoBaner(new AttachEntity(dto.getBanerId().getId()), video.getId());
        }

        return new ResposeDTO("succsess", true);
    }

    private VideoEntity checkPerMit(String id) {
        Optional<VideoEntity> entity = videoRepository.findByIdAndVisibleTrue(id);
        if (entity.isEmpty()) {
            throw new BadRequestException("not found playlist");
        }

        if (!entity.get().getProfile().getId().equals(getProfile().getId())) {
            throw new BadRequestException("not permish");
        }

        return entity.get();
    }

    private VideoEntity checkPerMitAdmin(String id) {
        Optional<VideoEntity> entity = videoRepository.findByIdAndVisibleTrue(id);
        if (entity.isEmpty()) {
            throw new BadRequestException("not found playlist");
        }

        VideoEntity videoEntity = entity.get();

        if (!videoEntity.getProfile().getId().equals(getProfile().getId())) {
            if (!getProfile().getRole().name().equals("ROLE_ADMIN")) {
                throw new BadRequestException("not permish");
            }
        }

        return videoEntity;
    }

    public VideoShortInfoDTO getVideoShorInfoDTO(VideoShortInfoRepository infoRepository) {
        VideoShortInfoDTO videoShorInfoDTO = new VideoShortInfoDTO();
        videoShorInfoDTO.setId(infoRepository.getId());
        videoShorInfoDTO.setTitle(infoRepository.getTitle());
        videoShorInfoDTO.setPreview(new AttachDTO(infoRepository.getPreview()));

        if (infoRepository.getPreview() != null) {
            videoShorInfoDTO.setPreviewUrl(serverUrl + "" + "attache/open/" + infoRepository.getPreview());
        } else {
            videoShorInfoDTO.setPreviewUrl(null);
        }

        videoShorInfoDTO.setViewCount(infoRepository.getViewCount());
        videoShorInfoDTO.setPublishedDate(infoRepository.getPublishedDate());

        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setPhotoId(new AttachDTO(infoRepository.getchannelPhoto()));
        channelDTO.setUrl(serverUrl + "" + "attache/open/" + infoRepository.getchannelPhoto());
        channelDTO.setName(infoRepository.getchannelName());

        videoShorInfoDTO.setChannel(channelDTO);

        return videoShorInfoDTO;

    }

    public VideoDTO toDTO(VideoEntity videoEntity) {
        VideoDTO response = new VideoDTO();
        response.setId(videoEntity.getId());
        response.setTitle(videoEntity.getTitle());
        response.setDescription(videoEntity.getDescription());

        if (videoEntity.getPreview() == null) {
            response.setPreview(null);
        } else {
            response.setPreview(new AttachDTO(videoEntity.getPreview().getId()));
        }

        if (videoEntity.getChannel()==null){
            response.setChannel(null);
        }else {

            response.setChannel(new ChannelDTO(videoEntity.getChannel().getId()));
        }


        if (videoEntity.getPhoto() == null) {
            response.setPhoto(null);
        } else {
            response.setPhoto(new AttachDTO(videoEntity.getPhoto().getId()));
        }

        if (videoEntity.getBaner() == null) {
            response.setBaner(null);
        } else {
            response.setBaner(new AttachDTO(videoEntity.getBaner().getId()));
        }


        response.setStatus(videoEntity.getStatus());
        response.setType(videoEntity.getType());
        response.setProfile(new ProfileDTO(videoEntity.getProfile().getId()));
        response.setDisLikeCount(videoEntity.getDisLikeCount());
        response.setLikeCount(videoEntity.getLikeCount());
        response.setViewCount(videoEntity.getViewCount());
        response.setSharedCount(videoEntity.getSharedCount());
        response.setTagList(videoTagService.getList(videoEntity));
        response.setCategoryList(videoCategoryService.getList(videoEntity));

        return response;
    }


    public VideoEntity getVideo(String id) {
        return videoRepository.findByIdAndVisibleTrue(id).orElseThrow(() -> {
            throw new ItemNotFoundEseption("video not found");
        });
    }



    public VideoFullInfoDTO getById(String id) {
        ProfileEntity profile = getProfile();
        System.out.println(profile.getId());

        Optional<VideoEntity> optional
                = videoRepository.findByIdAndVisibleTrue(id);

    /*    if (optional.isEmpty()) {
            throw new BadRequestException(
                    "Video not found!"
            );
        }*/

        VideoEntity video = optional.get();

  /*      if (video.getStatus().equals(VideoStatus.PRIVATE) &&
                !profile.getRole().equals(ProfileRole.ROLE_ADMIN)) {

            throw new BadRequestException(
                    "Method not allowed!"
            );
        }*/

        VideoFullInfo entity = videoRepository.getByIdWithFullInfo( profile.getId(), id);

        return toDTOWithFullInfo(entity);
    }

    private VideoFullInfoDTO toDTOWithFullInfo(
            VideoFullInfo entity) {
        VideoFullInfoDTO dto = new VideoFullInfoDTO();

        VideoDTO videoDTO = new VideoDTO();
        if (entity==null){
            videoDTO.setId(null);
        }else {
            videoDTO.setId(entity.getVideoId());
        }
        videoDTO.setTitle(entity.getVideoName());
        videoDTO.setDescription(entity.getVideoDescription());
        videoDTO.setViewCount(entity.getViewCount());
        videoDTO.setSharedCount(entity.getSharedCount());


        dto.setVideo(videoDTO);

        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setId(entity.getChannelId());
        channelDTO.setName(entity.getChannelName());
        channelDTO.setPhotoId(new AttachDTO(entity.getChannelPhotoId()));

        dto.setChannel(channelDTO);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(entity.getCategoryId());
        categoryDTO.setName(entity.getCategoryName());

        dto.setCategory(categoryDTO);

        AttachDTO preview = new AttachDTO();
        preview.setId(entity.getPreviewId());
 /*       preview.setUrl(attachService.getUrl(preview.getId()));*/

        dto.setPreview(preview);

        AttachDTO attach = new AttachDTO();
        attach.setId(entity.getAttachId());
/*        attach.setUrl(attachService.getUrl(attach.getId()));*/

        dto.setAttach(attach);

        List<TagDTO> tagList = tagService.getTagListByVideoId(entity.getVideoId());


        dto.setTagList(tagList);

        dto.setLikeCount(entity.getVideoLikeCount());
        dto.setDislikeCount(entity.getVideoDislikeCount());
        dto.setIsUserLiked(entity.getIsUserLiked());

        return dto;
    }



}

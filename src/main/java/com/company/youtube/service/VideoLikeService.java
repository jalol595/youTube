package com.company.youtube.service;

import com.company.youtube.config.CustomUserDetails;
import com.company.youtube.dto.attach.AttachDTO;
import com.company.youtube.dto.channel.ChannelDTO;
import com.company.youtube.dto.video.VideoDTO;
import com.company.youtube.dto.videoLike.VideoLikeInfoDTO;
import com.company.youtube.entity.ProfileEntity;
import com.company.youtube.entity.VideoEntity;
import com.company.youtube.entity.VideoLikeEntity;
import com.company.youtube.enums.VideoLikeStatus;
import com.company.youtube.exseptions.BadRequestException;
import com.company.youtube.exseptions.ItemNotFoundEseption;
import com.company.youtube.mapper.VideoLikeInfoRepository;
import com.company.youtube.repository.VideoLikeRepository;
import com.company.youtube.repository.VideoRepository;
import com.company.youtube.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class VideoLikeService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoLikeRepository videoLikeRepository;

    @Autowired
    private VideoService videoService;

    public void videoLike(String videoId) {
        likeDislike(videoId, VideoLikeStatus.LIKE);
    }

    public void videoDisLike(String videoId) {
        likeDislike(videoId, VideoLikeStatus.DIS_LIKE);

    }

    private void likeDislike(String videoId, VideoLikeStatus status) {


        if (!videoService.get(videoId)) {
            throw new BadRequestException("visible false");
        }

        Optional<VideoLikeEntity> optional = videoLikeRepository.findExists(videoId, getProfile().getId());

        if (optional.isPresent()) {
            VideoLikeEntity like = optional.get();
            like.setLikeStatus(status);
            videoLikeRepository.save(like);
            return;
        }
        boolean videoExists = videoRepository.existsById(videoId);
        if (!videoExists) {
            throw new ItemNotFoundEseption("video NotFound");
        }

        VideoLikeEntity videoLike = new VideoLikeEntity();
        videoLike.setVideo(new VideoEntity(videoId));
        videoLike.setProfile(new ProfileEntity(getProfile().getId()));
        videoLike.setLikeStatus(status);
        videoLikeRepository.save(videoLike);
    }

    public void removeLike(String videoId) {
        videoLikeRepository.delete(videoId, getProfile().getId());
    }

    public List<VideoLikeInfoDTO> userLikedVideoList() {

        List<VideoLikeInfoRepository> repositories = videoLikeRepository.userLikedVideoList(getProfile().getId());
        List<VideoLikeInfoDTO> dtoList = new LinkedList<>();
        repositories.forEach(videoLikeInfoRepository -> {

            VideoLikeInfoDTO videoLikeInfoDTO = new VideoLikeInfoDTO();
            videoLikeInfoDTO.setId(videoLikeInfoRepository.getId());

            VideoDTO videoDTO = new VideoDTO();
            videoDTO.setId(videoLikeInfoRepository.getVideoId());
            videoDTO.setTitle(videoLikeInfoRepository.getVideoTitle());
            videoDTO.setPreview(new AttachDTO(videoLikeInfoRepository.getVideoPreview()));

            videoLikeInfoDTO.setVideoDTO(videoDTO);

            ChannelDTO channelDTO = new ChannelDTO();
            channelDTO.setId(videoLikeInfoRepository.getchannelId());
            channelDTO.setName(videoLikeInfoRepository.getchannelName());
            videoLikeInfoDTO.setChannelDTO(channelDTO);

            dtoList.add(videoLikeInfoDTO);
        });

        return dtoList;
    }

    public List<VideoLikeInfoDTO> getUserLikedVideoListByUserId(Integer id) {

        if (!getProfile().getRole().name().equals("ROLE_ADMIN")) {
            throw new BadRequestException("not permish");
        }

        List<VideoLikeInfoRepository> repositories = videoLikeRepository.userLikedVideoList(id);
        List<VideoLikeInfoDTO> dtoList = new LinkedList<>();
        repositories.forEach(videoLikeInfoRepository -> {
            VideoLikeInfoDTO videoLikeInfoDTO = new VideoLikeInfoDTO();
            videoLikeInfoDTO.setId(videoLikeInfoRepository.getId());

            VideoDTO videoDTO = new VideoDTO();
            videoDTO.setId(videoLikeInfoRepository.getVideoId());
            videoDTO.setTitle(videoLikeInfoRepository.getVideoTitle());
            videoDTO.setPreview(new AttachDTO(videoLikeInfoRepository.getVideoPreview()));

            videoLikeInfoDTO.setVideoDTO(videoDTO);

            ChannelDTO channelDTO = new ChannelDTO();
            channelDTO.setId(videoLikeInfoRepository.getchannelId());
            channelDTO.setName(videoLikeInfoRepository.getchannelName());
            videoLikeInfoDTO.setChannelDTO(channelDTO);

            dtoList.add(videoLikeInfoDTO);
        });

        return dtoList;
    }

    public ProfileEntity getProfile() {
        CustomUserDetails user = SpringSecurityUtil.getCurrentUser();
        return user.getProfile();
    }


}

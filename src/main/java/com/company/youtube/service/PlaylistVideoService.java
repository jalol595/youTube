package com.company.youtube.service;

import com.company.youtube.config.CustomUserDetails;
import com.company.youtube.dto.attach.AttachDTO;
import com.company.youtube.dto.channel.ChannelDTO;
import com.company.youtube.dto.playlistVideo.PlaylistVideoDTO;
import com.company.youtube.dto.playlistVideo.PlaylistVideoShortInfoDTO;
import com.company.youtube.dto.playlistVideo.ResponsePlaylistVideoDTO;
import com.company.youtube.dto.playlistVideo.UpdatePlaylistVideoDTO;
import com.company.youtube.dto.response.ResposeDTO;
import com.company.youtube.dto.video.VideoDTO;
import com.company.youtube.entity.PlaylistEntity;
import com.company.youtube.entity.PlaylistVideoEntity;
import com.company.youtube.entity.ProfileEntity;
import com.company.youtube.entity.VideoEntity;
import com.company.youtube.exseptions.BadRequestException;
import com.company.youtube.mapper.playlist.PlaylistShortInfoRepository;
import com.company.youtube.mapper.playlistVideo.PlaylistVideoInfoRepository;
import com.company.youtube.mapper.video.VideoShortInfoRepository;
import com.company.youtube.repository.PlaylistVideoRepository;
import com.company.youtube.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistVideoService {

    @Autowired
    private PlaylistVideoRepository playlistVideoRepository;

    @Autowired
    private PlayListService playListService;

    @Autowired
    private VideoService videoService;


    @Value("${server.url}")
    private String serverUrl;

    public ResponsePlaylistVideoDTO create(PlaylistVideoDTO dto) {
        VideoEntity video = checkPerMit(dto.getVideo());
        PlaylistEntity playlist = playListService.get(dto.getPlaylist());
        PlaylistVideoEntity playlistVideo = new PlaylistVideoEntity();
        playlistVideo.setVideo(video);
        playlistVideo.setPlaylist(playlist);
        playlistVideoRepository.save(playlistVideo);
        playlistVideoRepository.updateOerderNum(playlistVideo.getId(), playlistVideo.getId());

        ResponsePlaylistVideoDTO playlistVideoDTO = new ResponsePlaylistVideoDTO();
        playlistVideoDTO.setPlaylist(playListService.toDTO(playlistVideo.getPlaylist()));
        playlistVideoDTO.setVideo(videoService.toDTO(playlistVideo.getVideo()));
        playlistVideoDTO.setOrderNum(playlistVideo.getOrderNum());

        return playlistVideoDTO;
    }

    public ResposeDTO update(Integer id, PlaylistVideoDTO dto) {

        Optional<PlaylistVideoEntity> repository = playlistVideoRepository.findById(id);
        if (repository.isEmpty()) {
            throw new BadRequestException("not found");
        }

        PlaylistVideoEntity playlistVideoEntity = repository.get();

        VideoEntity video = checkPerMit(dto.getVideo());
        PlaylistEntity playlist = playListService.get(dto.getPlaylist());

        playlistVideoEntity.setVideo(new VideoEntity(dto.getNewVideo()));
        playlistVideoEntity.setPlaylist(new PlaylistEntity(dto.getNewPlaylist()));
        playlistVideoEntity.setOrderNum(dto.getOrderNume());
        playlistVideoRepository.save(playlistVideoEntity);



        return new ResposeDTO("succsess", true);
    }

    public ResposeDTO delete(Integer id, PlaylistVideoDTO dto) {
        VideoEntity video = checkPerMit(dto.getVideo());
        PlaylistEntity playlist = playListService.get(dto.getPlaylist());

        Optional<PlaylistVideoEntity> repository =
                playlistVideoRepository.findByIdAndPlaylistAndVideo(id, playlist, video);
        if (repository.isEmpty()) {
            throw new BadRequestException("not found");
        }

        playlistVideoRepository.updateVsibl(repository.get().getId());

        return new ResposeDTO("succsess", true);
    }

    private VideoEntity checkPerMit(String id) {
        VideoEntity entity = videoService.getVideo(id);

        if (!entity.getProfile().getId().equals(getProfile().getId())) {
            throw new BadRequestException("not permish");
        }

        return entity;
    }

    public ProfileEntity getProfile() {
        CustomUserDetails user = SpringSecurityUtil.getCurrentUser();
        return user.getProfile();
    }

    public List<PlaylistVideoShortInfoDTO> getVideolistByplayListId(Integer id) {

        List<PlaylistVideoInfoRepository> list = playlistVideoRepository.getVideolistByplayListId(id);
        List<PlaylistVideoShortInfoDTO> dtoList = new LinkedList<>();

        list.forEach(playlistVideoInfoRepository -> {
            PlaylistVideoShortInfoDTO infoDTO = new PlaylistVideoShortInfoDTO();
            infoDTO.setId(playlistVideoInfoRepository.getId());
            infoDTO.setOrderNum(playlistVideoInfoRepository.getOderNum());
            infoDTO.setCreatedDate(playlistVideoInfoRepository.getCreatedDate());

            VideoDTO videoDTO = new VideoDTO();
            videoDTO.setId(playlistVideoInfoRepository.getVideoId());
            videoDTO.setPreview(new AttachDTO(playlistVideoInfoRepository.getVideoPreview()));
            videoDTO.setPreUrl(serverUrl + "" + "attache/open/" + playlistVideoInfoRepository.getVideoPreview());

            infoDTO.setVideo(videoDTO);

            ChannelDTO channelDTO = new ChannelDTO();
            channelDTO.setId(playlistVideoInfoRepository.getchannelId());
            channelDTO.setName(playlistVideoInfoRepository.getchannelName());


            infoDTO.setChannelDTO(channelDTO);
            dtoList.add(infoDTO);

        });

        return dtoList;
    }

    public List<VideoDTO> getVideoListByPlaylistId(Integer playlistId) {
        List<VideoEntity> list = playlistVideoRepository.findTop2ByPlaylistId(playlistId);

        List<VideoDTO> dtoList = new LinkedList<>();
        list.forEach(entity -> {
            VideoDTO dto = new VideoDTO();
            dto.setId(entity.getId());
            dto.setTitle(entity.getTitle());

            dtoList.add(dto);
        });

        return dtoList;
    }
}

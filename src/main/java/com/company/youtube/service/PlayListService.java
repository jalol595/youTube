package com.company.youtube.service;

import com.company.youtube.config.CustomUserDetails;
import com.company.youtube.dto.attach.AttachDTO;
import com.company.youtube.dto.channel.ChannelDTO;
import com.company.youtube.dto.playlist.*;
import com.company.youtube.dto.profile.ProfileDTO;
import com.company.youtube.dto.response.ResposeDTO;
import com.company.youtube.dto.video.VideoDTO;
import com.company.youtube.dto.video.VideoShortInfo;
import com.company.youtube.dto.video.VideoShortInfoDTO;
import com.company.youtube.entity.ChannelEntity;
import com.company.youtube.entity.PlaylistEntity;
import com.company.youtube.entity.ProfileEntity;
import com.company.youtube.exseptions.AlreadyExist;
import com.company.youtube.exseptions.BadRequestException;
import com.company.youtube.exseptions.ItemNotFoundEseption;
import com.company.youtube.mapper.PlaylistFullInfo;
import com.company.youtube.mapper.PlaylistShortInfo;
import com.company.youtube.mapper.playlist.PlaylistShortInfoRepository;
import com.company.youtube.mapper.playlist.PlaylistInfoRepository;
import com.company.youtube.repository.PlaylistRepository;
import com.company.youtube.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayListService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private VideoService videoService;

    @Autowired
    @Lazy
    private PlaylistVideoService playlistVideoService;

    @Autowired
    private AttachService attachService;

    @Value("${server.url}")
    private String serverUrl;

    public PlaylistDTO create(PlaylistDTO dto) {

        Optional<PlaylistEntity> playlistEntity = playlistRepository.findByName(dto.getTitle());
        if (playlistEntity.isPresent()) {
            throw new AlreadyExist("Already exists name");
        }

        ChannelEntity channel = channelService.get(dto.getChannel().getId());

        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setChannel(channel);
        playlist.setName(dto.getTitle());
        playlist.setDescription(dto.getDecription());
        playlist.setStatus(dto.getStatus());
        playlist.setCreatedDate(LocalDateTime.now());
        playlistRepository.save(playlist);
        playlistRepository.updateOerderNum(playlist.getId(), playlist.getId());
        return toDTO(playlist);

    }

    public ResposeDTO update(Integer id, UpdatePlayListDTO dto) {
        PlaylistEntity playlist = checkPerMit(id);
        playlistRepository.updateNameAndDescription(dto.getName(), dto.getDescription(), playlist.getId());
        return new ResposeDTO("succsess", true);
    }

    public ResposeDTO changeStatus(Integer id, ChangeStatusPlayListDTO dto) {
        PlaylistEntity playlist = checkPerMit(id);
        playlistRepository.changeStatus(dto.getStatus(), playlist.getId());
        return new ResposeDTO("succsess", true);
    }

    public ResposeDTO delete(Integer id) {
        PlaylistEntity playlist = checkPerMitAdmin(id);
        playlistRepository.updateVisible(playlist.getId());
        return new ResposeDTO("succsess", true);
    }

    public PageImpl pagination(int page, int size) {

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PlaylistEntity> all = playlistRepository.findAll(pageable);
        List<PlaylistEntity> list = all.getContent();
        List<PlaylistInfoDTO> dtoList = new LinkedList<>();
        list.forEach(playlist -> {
            dtoList.add(getInfo(playlist));
        });

        return new PageImpl(dtoList, pageable, all.getTotalElements());
    }

    public List<PlaylistInfoDTO> listByUserId(Integer id) {

        if (!getProfile().getRole().name().equals("ROLE_ADMIN")) {
            throw new BadRequestException("not permish");
        }

        List<PlaylistInfoRepository> infoList = playlistRepository.getPlaylistUserId(id);
        List<PlaylistInfoDTO> list = new LinkedList<>();

        infoList.forEach(playlstInfo -> {
            list.add(getInfoRepository(playlstInfo));
        });
        return list;
    }

    public List<PlaylistDTO> getProfilePLayList() {
        List<PlaylistDTO> playlist = new LinkedList<>();

        ProfileEntity profileEntity = getProfile();
        List<PlaylistShortInfo> entityList = playlistRepository.getProfilePlayLists(profileEntity.getId());

        for (PlaylistShortInfo entity : entityList) {
            PlaylistDTO dto = new PlaylistDTO();
            dto.setId(entity.getPlaylistId());
            dto.setTitle(entity.getPlaylistName());
            dto.setCreatedDate(entity.getPlaylistCreatedDate());
            dto.setChannel(new ChannelDTO(entity.getChannelId(), entity.getChannelName()));
            dto.setVideoCount(entity.getCountVideo());

            List<VideoPlaylistInfoDTO> videoPlaylistInfoList = videoService.getPlaylistFirstTwoVideo(entity.getPlaylistId());
            dto.setVideoPlaylistInfo(videoPlaylistInfoList);

            playlist.add(dto);
        }
        return playlist;
    }

    public List<PlaylistDTO> playListByChannelKey(String key) {

        List<PlaylistDTO> playlist = new LinkedList<>();

        List<PlaylistShortInfo> entityList = playlistRepository.playListByChannelKey(key);

        for (PlaylistShortInfo entity : entityList) {
            PlaylistDTO dto = new PlaylistDTO();
            dto.setId(entity.getPlaylistId());
            dto.setTitle(entity.getPlaylistName());
            dto.setCreatedDate(entity.getPlaylistCreatedDate());
            dto.setChannel(new ChannelDTO(entity.getChannelId(), entity.getChannelName()));
            dto.setVideoCount(entity.getCountVideo());

            List<VideoPlaylistInfoDTO> videoPlaylistInfoList = videoService.getPlaylistFirstTwoVideo(entity.getPlaylistId());
            dto.setVideoPlaylistInfo(videoPlaylistInfoList);

            playlist.add(dto);
        }
        return playlist;

    }

    public PlaylistShortInfoDTO getById(Integer playlistId) {
        Optional<PlaylistShortInfo> optional = playlistRepository.getById(playlistId);

        if (optional.isEmpty()) {
            throw new BadRequestException(
                    "Playlist not found!"
            );
        }

        PlaylistShortInfo info = optional.get();

        return toDTO(info);


    }


    public PlaylistInfoDTO getInfo(PlaylistEntity playlist) {

        PlaylistInfoDTO infoDTO = new PlaylistInfoDTO();
        infoDTO.setName(playlist.getName());
        infoDTO.setDescription(playlist.getDescription());
        infoDTO.setStatus(playlist.getStatus());
        infoDTO.setOrderNum(playlist.getOrderNum());

        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setId(playlist.getChannel().getId());
        channelDTO.setName(playlist.getChannel().getName());

        if (playlist.getChannel().getPhoto() != null) {
            channelDTO.setPhotoId(new AttachDTO(playlist.getChannel().getPhoto().getId()));
        } else {
            channelDTO.setPhotoId(null);
        }


        if (playlist.getChannel().getPhoto() != null) {
            channelDTO.setUrl(serverUrl + "" + "attache/open/" + playlist.getChannel().getPhoto().getId());
        } else {
            channelDTO.setUrl(null);
        }
        infoDTO.setChannel(channelDTO);

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(playlist.getChannel().getProfile().getId());
        profileDTO.setName(playlist.getChannel().getProfile().getName());
        profileDTO.setSurname(playlist.getChannel().getProfile().getSurname());

        if (playlist.getChannel().getProfile().getPhoto() != null) {
            profileDTO.setPhototId(new AttachDTO(playlist.getChannel().getProfile().getPhoto().getId()));
        } else {
            profileDTO.setPhototId(null);
        }


        if (playlist.getChannel().getProfile().getPhoto() != null) {
            profileDTO.setUrl(serverUrl + "" + "attache/open/" + playlist.getChannel().getProfile().getPhoto().getId());
        } else {
            profileDTO.setUrl(null);
        }

        infoDTO.setProfile(profileDTO);
        return infoDTO;
    }

    public PlaylistInfoDTO getInfoRepository(PlaylistInfoRepository playlstInfo) {

        PlaylistInfoDTO infoDTO = new PlaylistInfoDTO();
        infoDTO.setId(playlstInfo.getId());
        infoDTO.setName(playlstInfo.getName());
        infoDTO.setDescription(playlstInfo.getDescription());
        infoDTO.setStatus(playlstInfo.getStatus());
        infoDTO.setOrderNum(playlstInfo.getOrderNum());

        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setId(playlstInfo.getChannelId());
        channelDTO.setName(playlstInfo.getChannelName());

        if (playlstInfo.getChannelPhotoId() != null) {
            channelDTO.setPhotoId(new AttachDTO(playlstInfo.getChannelPhotoId()));
        } else {
            channelDTO.setPhotoId(null);
        }


        if (playlstInfo.getChannelPhotoId() != null) {
            channelDTO.setUrl(serverUrl + "" + "attache/open/" + playlstInfo.getChannelPhotoId());
        } else {
            channelDTO.setUrl(null);
        }
        infoDTO.setChannel(channelDTO);

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(playlstInfo.getProfileId());
        profileDTO.setName(playlstInfo.getProfileName());
        profileDTO.setSurname(playlstInfo.getProfileSurname());

        if (playlstInfo.getProfilePhotoId() != null) {
            profileDTO.setPhototId(new AttachDTO(playlstInfo.getProfilePhotoId()));
        } else {
            profileDTO.setPhototId(null);
        }


        if (playlstInfo.getProfilePhotoId() != null) {
            profileDTO.setUrl(serverUrl + "" + "attache/open/" + playlstInfo.getProfilePhotoId());
        } else {
            profileDTO.setUrl(null);
        }

        infoDTO.setProfile(profileDTO);

        return infoDTO;
    }

    public PlaylistDTO toDTO(PlaylistEntity playlist) {
        PlaylistDTO dto = new PlaylistDTO();
        dto.setId(playlist.getId());
        dto.setChannel(dto.getChannel());
        dto.setTitle(playlist.getName());
        dto.setDecription(playlist.getDescription());
        dto.setStatus(playlist.getStatus());
        dto.setOrder(playlist.getId());
        dto.setCreatedDate(playlist.getCreatedDate());
        return dto;
    }

    private PlaylistEntity checkPerMit(Integer id) {
        Optional<PlaylistEntity> entity = playlistRepository.findByIdAndVisibleTrue(id);
        if (entity.isEmpty()) {
            throw new BadRequestException("not found playlist");
        }

        ChannelEntity channel = channelService.get(entity.get().getChannel().getId());

        if (!channel.getProfile().getId().equals(getProfile().getId())) {
            throw new BadRequestException("not permish");
        }

        return entity.get();
    }

    private PlaylistEntity checkPerMitAdmin(Integer id) {
        Optional<PlaylistEntity> entity = playlistRepository.findByIdAndVisibleTrue(id);
        if (entity.isEmpty()) {
            throw new BadRequestException("not found playlist");
        }

        ChannelEntity channel = channelService.get(entity.get().getChannel().getId());

        if (!channel.getProfile().getId().equals(getProfile().getId())) {
            if (!getProfile().getRole().name().equals("ROLE_ADMIN")) {
                throw new BadRequestException("not permish");
            }
        }

        return entity.get();
    }

    public ProfileEntity getProfile() {
        CustomUserDetails user = SpringSecurityUtil.getCurrentUser();
        return user.getProfile();
    }

    public PlaylistEntity get(Integer id) {
        return playlistRepository.findByIdAndVisibleTrue(id).orElseThrow(() -> {
            throw new ItemNotFoundEseption("palylist not found");
        });
    }

    private PlaylistShortInfoDTO toDTO(PlaylistShortInfo info) {
        PlaylistShortInfoDTO dto = new PlaylistShortInfoDTO();

        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setId(info.getPlaylistId());
        playlistDTO.setTitle(info.getPlaylistName());
        playlistDTO.setCreatedDate(info.getPlaylistCreatedDate());

        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setId(info.getChannelId());
        channelDTO.setName(info.getChannelName());

        List<VideoDTO> videoList = playlistVideoService.
                getVideoListByPlaylistId(info.getPlaylistId());

        dto.setVideoCount(info.getCountVideo());
        dto.setTotalViewCount(info.getTotalWatchedCount());
        dto.setPlaylist(playlistDTO);
        dto.setChannel(channelDTO);
        dto.setVideoList(videoList);

        return dto;
    }

}

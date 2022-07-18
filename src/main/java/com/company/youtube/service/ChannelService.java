package com.company.youtube.service;

import com.company.youtube.config.CustomUserDetails;
import com.company.youtube.dto.attach.UpdateAttachDTO;
import com.company.youtube.dto.channel.ChannelDTO;
import com.company.youtube.dto.attach.AttachDTO;
import com.company.youtube.dto.channel.UpdateChannelDTO;
import com.company.youtube.dto.channel.UpdateChannelPhotoDTO;
import com.company.youtube.dto.response.ResposeDTO;
import com.company.youtube.entity.AttachEntity;
import com.company.youtube.entity.ChannelEntity;
import com.company.youtube.entity.ProfileEntity;
import com.company.youtube.enums.ChannelStatus;
import com.company.youtube.exseptions.AlreadyExist;
import com.company.youtube.exseptions.BadRequestException;
import com.company.youtube.exseptions.ItemNotFoundEseption;
import com.company.youtube.repository.ChannelRepository;
import com.company.youtube.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private AttachService attachService;

    public ChannelDTO create(ChannelDTO dto) {

        if (channelRepository.existsByNameAndVisible(dto.getName(), true)) {
            throw new AlreadyExist("Alredy exist");
        }

        ChannelEntity channel = new ChannelEntity();
        channel.setName(dto.getName());
        channel.setDescription(dto.getDescription());
        channel.setStatus(ChannelStatus.ACTIVE);

        if (dto.getPhotoId() == null) {
            channel.setPhoto(null);
        } else {
            channel.setPhoto(new AttachEntity(dto.getPhotoId().getId()));
        }

        if (dto.getBanerId() == null) {
            channel.setBaner(null);
        } else {
            channel.setBaner(new AttachEntity(dto.getBanerId().getId()));
        }


        channel.setProfile(getProfile());

        channel.setWebsiteUrl(checkColumn(dto.getWebsiteUrl()));
        channel.setTelegramUrl(checkColumn(dto.getTelegramUrl()));
        channel.setInstagramUrl(checkColumn(dto.getInstagramUrl()));
        channel.setCreatedDate(LocalDateTime.now());

        channelRepository.save(channel);
        return toDTO(channel);

    }

    public ChannelDTO update(String id, UpdateChannelDTO dto) {

        Optional<ChannelEntity> entity = channelRepository.findByIdAndStatusAndVisibleTrue(id, ChannelStatus.ACTIVE);

        if (entity.isEmpty()) {
            throw new ItemNotFoundEseption("not found");
        } else if (!entity.get().getProfile().getId().equals(getProfile().getId())) {
            throw new BadRequestException("not permish");
        }

        ChannelEntity channel = entity.get();
        channel.setName(dto.getName());
        channel.setDescription(dto.getDescription());
        channel.setInstagramUrl(dto.getInstagramUrl());
        channel.setTelegramUrl(dto.getTelegramUrl());
        channel.setWebsiteUrl(dto.getWebsiteUrl());
        channelRepository.save(channel);
        return toDTO(channel);
    }

    public ResposeDTO updatePhoto(String id, UpdateChannelPhotoDTO dto) {
        return updatePhotoChannel(id, dto);
    }

    public ResposeDTO updateBaner(String id, UpdateChannelPhotoDTO dto) {
        return updatePhotoChannelBaner(id, dto);
    }

    public PageImpl pagination(int page, int size) {

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ChannelEntity> all = channelRepository.findAll(pageable);
        List<ChannelEntity> list = all.getContent();
        List<ChannelDTO> dtoList = new LinkedList<>();
        list.forEach(channel -> {
            dtoList.add(toDTO(channel));
        });

        return new PageImpl(dtoList, pageable, all.getTotalElements());
    }

    public ChannelDTO getById(String id) {

        Optional<ChannelEntity> entity = channelRepository.findByIdAndStatusAndVisibleTrue(id, ChannelStatus.ACTIVE);

        if (entity.isEmpty()) {
            throw new ItemNotFoundEseption("not found");
        } else if (!entity.get().getProfile().getId().equals(getProfile().getId())) {
            throw new BadRequestException("not permish");
        }

        ChannelEntity channel = entity.get();
        return toDTO(channel);
    }

    public ResposeDTO delete(String id) {
        Optional<ChannelEntity> entity = channelRepository.findById(id);
        if (!entity.get().getProfile().getId().equals(getProfile().getId())) {
            if (!getProfile().getRole().name().equals("ROLE_ADMIN")) {
                throw new BadRequestException("not permish");
            }

        } else if (entity.isEmpty()) {
            throw new ItemNotFoundEseption("not found");
        }

        if (entity.get().getStatus().name().equals("BLOCK")) {
            throw new AlreadyExist("Already blocked");
        }

        channelRepository.updateStatusById(id);

        return new ResposeDTO("sucsess", true);
    }

    public List<ChannelDTO> getList() {

        Iterable<ChannelEntity> all = channelRepository.getList(getProfile().getId());
        List<ChannelDTO> dtoList = new LinkedList<>();

        all.forEach(channel -> {
            dtoList.add(toDTO(channel));
        });
        return dtoList;
    }

    public ProfileEntity getProfile() {
        CustomUserDetails user = SpringSecurityUtil.getCurrentUser();
        return user.getProfile();
    }

    private ResposeDTO updatePhotoChannel(String id, UpdateChannelPhotoDTO dto) {
        Optional<ChannelEntity> entity = channelRepository.findByIdAndStatusAndVisibleTrue(id, ChannelStatus.ACTIVE);

        if (entity.isEmpty()) {
            throw new ItemNotFoundEseption("not found");
        } else if (!entity.get().getProfile().getId().equals(getProfile().getId())) {
            throw new BadRequestException("not permish");
        }else if (entity.get().getPhoto() == null && dto.getPhotoId() == null) {
            throw new BadRequestException("rasm yoq edi");
        }



        ChannelEntity channel = entity.get();

        if (channel.getPhoto() != null && dto.getPhotoId() == null) {
            channelRepository.deletePhoto(null, channel.getId());
            attachService.delete(channel.getPhoto().getId());
        } else if (channel.getPhoto() != null && dto.getPhotoId().getId() != null
                && channel.getPhoto() != (new AttachEntity(dto.getPhotoId().getId()))) {
            channelRepository.updatePhoto(new AttachEntity(dto.getPhotoId().getId()), channel.getId());
            attachService.delete(channel.getPhoto().getId());
        } else if (channel.getPhoto() == null && dto.getPhotoId().getId() != null) {
            channel.setPhoto(new AttachEntity(dto.getPhotoId().getId()));
            channelRepository.updatePhoto(new AttachEntity(dto.getPhotoId().getId()), channel.getId());
        }


        return new ResposeDTO("succsess", true);

    }

    private ResposeDTO updatePhotoChannelBaner(String id, UpdateChannelPhotoDTO dto) {

        Optional<ChannelEntity> entity = channelRepository.findByIdAndStatusAndVisibleTrue(id, ChannelStatus.ACTIVE);

        if (entity.isEmpty()) {
            throw new ItemNotFoundEseption("not found");
        } else if (!entity.get().getProfile().getId().equals(getProfile().getId())) {
            throw new BadRequestException("not permish");
        }else if (entity.get().getBaner() == null && dto.getBanerId() == null) {
            throw new BadRequestException("baner yoq edi");
        }

        ChannelEntity channel = entity.get();

        if (channel.getBaner() != null && dto.getBanerId() == null) {
            channelRepository.deletePhotoBaner(null, channel.getId());
            attachService.delete(channel.getBaner().getId());
        } else if (channel.getBaner() != null && dto.getBanerId().getId() != null
                && channel.getPhoto() != (new AttachEntity(dto.getBanerId().getId()))) {
            channelRepository.updatePhotoBaner(new AttachEntity(dto.getBanerId().getId()), channel.getId());
            attachService.delete(channel.getBaner().getId());
        } else if (channel.getBaner() == null && dto.getBanerId().getId() != null) {
            channel.setBaner(new AttachEntity(dto.getBanerId().getId()));
            channelRepository.updatePhotoBaner(new AttachEntity(dto.getBanerId().getId()), channel.getId());
        }

        return new ResposeDTO("succsess", true);
    }

    private String checkColumn(String url) {
        return (url != null) ? url : null;
    }

    private ChannelDTO toDTO(ChannelEntity channel) {
        ChannelDTO response = new ChannelDTO();
        response.setId(channel.getId());
        response.setName(channel.getName());
        response.setDescription(channel.getDescription());
        if (channel.getPhoto() == null) {
            response.setPhotoId(null);
        } else {
            response.setPhotoId(new AttachDTO(channel.getPhoto().getId()));
        }

        if (channel.getBaner() == null) {
            response.setBanerId(null);
        } else {
            response.setBanerId(new AttachDTO(channel.getBaner().getId()));
        }

        response.setProfileId(getProfile().getId());
        response.setWebsiteUrl(channel.getWebsiteUrl());
        response.setTelegramUrl(channel.getTelegramUrl());
        response.setInstagramUrl(channel.getInstagramUrl());
        response.setCreatedDate(channel.getCreatedDate());
        response.setCreatedDate(channel.getCreatedDate());
        return response;
    }

    public ChannelEntity get(String id) {
        return channelRepository.findByIdAndStatusAndVisibleTrue(id, ChannelStatus.ACTIVE).orElseThrow(() -> {
            throw new ItemNotFoundEseption("Channel not found");
        });
    }

}

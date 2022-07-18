package com.company.youtube.service;

import com.company.youtube.config.CustomUserDetails;
import com.company.youtube.dto.profile.ChangePassworDTO;
import com.company.youtube.dto.profile.CreateProfileDTO;
import com.company.youtube.dto.profile.ProfileDTO;
import com.company.youtube.dto.profile.UpdateDetailDTO;
import com.company.youtube.dto.response.ResposeDTO;
import com.company.youtube.dto.attach.UpdateAttachDTO;
import com.company.youtube.entity.AttachEntity;
import com.company.youtube.entity.ProfileEntity;
import com.company.youtube.enums.ProfileStatus;
import com.company.youtube.exseptions.AlreadyExist;
import com.company.youtube.exseptions.BadRequestException;
import com.company.youtube.repository.ProfileRepository;
import com.company.youtube.util.BCryptUtil;
import com.company.youtube.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AttachService attachService;

    @Value("${server.url}")
    private String serverUrl;


    public ResposeDTO changePassword(ChangePassworDTO dto) {
        profileRepository.updatePassword(BCryptUtil.getBCrypt(dto.getPassword()), getProfile().getId());
        return new ResposeDTO("succsess", true);
    }

//    public String updateEmail(ProfileDTO dto) {
//
//        emailService.sendRegistrationEmail(getProfile().getEmail(), dto.getEmail(), getProfile().getId());
//        return "massage send";
//    }


    public ResposeDTO getById(String email, Integer id) {
        profileRepository.updateEmail(email, id);
        return new ResposeDTO("sucsess", true);
    }

    public ResposeDTO updateDetail(UpdateDetailDTO dto) {
        profileRepository.updateDetail(dto.getName(), dto.getSurname(), getProfile().getId());
        return new ResposeDTO("succsess", true);
    }

    public ResposeDTO updatePhoto(UpdateAttachDTO dto) {

        if (getProfile().getPhoto() != null && dto.getPhotoId() == null) {
            profileRepository.deletePhoto(null, getProfile().getId());
            attachService.delete(getProfile().getPhoto().getId());
        } else if (getProfile().getPhoto() != null && dto.getPhotoId() != null &&
                !getProfile().getPhoto().getId().equals(dto.getPhotoId())) {
            profileRepository.updatePhoto(new AttachEntity(dto.getPhotoId()), getProfile().getId());
            attachService.delete(getProfile().getPhoto().getId());
        } else if (getProfile().getPhoto() == null && dto.getPhotoId() != null) {
            getProfile().setPhoto(new AttachEntity(dto.getPhotoId()));
            profileRepository.updatePhoto(new AttachEntity(dto.getPhotoId()), getProfile().getId());
        }

        return new ResposeDTO("succsess", true);
    }

    public ProfileDTO getDetail() {
        ProfileEntity profile = getProfile();
        return toDTODetail(profile);
    }

    public ProfileDTO create(CreateProfileDTO dto) {
        if (profileRepository.existsByEmail(dto.getEmail())){
            throw new AlreadyExist("Already exists");
        }

        ProfileEntity profileEntity = toEntity(dto);
        profileRepository.save(profileEntity);
        return toDTO(profileEntity);
    }

    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> {
            throw new BadRequestException("User not found");
        });
    }

    public ProfileEntity getProfile() {
        CustomUserDetails user = SpringSecurityUtil.getCurrentUser();
        return user.getProfile();
    }

    private ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setPassword(null);
        dto.setEmail(entity.getEmail());

        return dto;
    }

    private ProfileDTO toDTODetail(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setPassword(null);
        dto.setEmail(entity.getEmail());

        if (entity.getPhoto() != null) {
            dto.setUrl(serverUrl + "" + "attache/open/" + entity.getPhoto().getId());
        }else {
            dto.setUrl(null);
        }

        return dto;
    }

    private ProfileEntity toEntity(ProfileDTO dto) {
        ProfileEntity profile = new ProfileEntity();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setEmail(dto.getEmail());
        profile.setPassword(BCryptUtil.getBCrypt(dto.getPassword()));
        profile.setRole(dto.getRole());
        profile.setStatus(ProfileStatus.ACTIVE);
        profile.setCreatedDate(LocalDateTime.now());
        return profile;
    }

    private ProfileEntity toEntity(CreateProfileDTO dto) {
        ProfileEntity profile = new ProfileEntity();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setEmail(dto.getEmail());
        profile.setPassword(BCryptUtil.getBCrypt(dto.getPassword()));
        profile.setRole(dto.getRole());
        profile.setStatus(ProfileStatus.ACTIVE);
        profile.setCreatedDate(LocalDateTime.now());
        return profile;
    }


}

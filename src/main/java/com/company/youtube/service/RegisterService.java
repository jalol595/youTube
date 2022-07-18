package com.company.youtube.service;

import com.company.youtube.dto.profile.RegisterDTO;
import com.company.youtube.entity.AttachEntity;
import com.company.youtube.entity.EmailHistoryEntity;
import com.company.youtube.entity.ProfileEntity;
import com.company.youtube.enums.ProfileRole;
import com.company.youtube.enums.ProfileStatus;
import com.company.youtube.exseptions.AlreadyExist;
import com.company.youtube.exseptions.BadRequestException;
import com.company.youtube.repository.ProfileRepository;
import com.company.youtube.util.BCryptUtil;
import com.company.youtube.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RegisterService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private EmailService emailService;

    public String register(RegisterDTO dto) {

        Optional<ProfileEntity> entity = profileRepository.findByEmail(dto.getEmail());
        if (entity.isPresent()){
            throw new AlreadyExist("Already exisit");
        }

        ProfileEntity profile=new ProfileEntity();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setEmail(dto.getEmail());
        profile.setPassword(BCryptUtil.getBCrypt(dto.getPassword()));
        profile.setRole(ProfileRole.ROLE_USER);

        if (dto.getPhotoId()==null){
            profile.setPhoto(null);
        }else {
            profile.setPhoto(new AttachEntity(dto.getPhotoId()));
        }

        profile.setStatus(ProfileStatus.NOT_ACTIVE);
        profile.setCreatedDate(LocalDateTime.now());

        profileRepository.save(profile);

        String token = JwtUtil.encode(profile.getId());


        emailService.sendRegistrationEmail(profile.getEmail(), token);


        return "Message was send";

    }


    public String verificationEmail(Integer pId) {

        Optional<ProfileEntity> profile = profileRepository.findById(pId);

        if (profile.isEmpty()){
            throw new BadRequestException("Profile not found!");
        }

        ProfileEntity profileEntity = profile.get();
        EmailHistoryEntity email = emailService.getEmail(profileEntity.getEmail());

        if (!email.getCreatedDate().plusMinutes(1).isAfter(LocalDateTime.now())){
            throw new BadRequestException("time out!");
        }

        profileRepository.updateStatusById(ProfileStatus.ACTIVE, pId);

        return "sucsess";

    }

}

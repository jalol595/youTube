package com.company.youtube.service;

import com.company.youtube.config.CustomUserDetails;
import com.company.youtube.dto.attach.AttachDTO;
import com.company.youtube.dto.comment.CommentDTO;
import com.company.youtube.dto.comment.CommentResponseDTO;
import com.company.youtube.dto.comment.CommentShortInfo;
import com.company.youtube.dto.profile.ProfileDTO;
import com.company.youtube.dto.response.ResposeDTO;
import com.company.youtube.dto.video.VideoDTO;
import com.company.youtube.entity.*;
import com.company.youtube.exseptions.AlreadyExist;
import com.company.youtube.exseptions.BadRequestException;
import com.company.youtube.mapper.comment.CommentResponseRepository;
import com.company.youtube.mapper.comment.CommentShortInfoRepository;
import com.company.youtube.repository.CommentRepository;
import com.company.youtube.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.stereotype.Service;

import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VideoService videoService;

    @Value("${server.url}")
    private String serverUrl;

    public CommentDTO create(CommentDTO dto) {
        VideoEntity video = videoService.getVideo(dto.getVideoId());

        Optional<CommentEntity> comment = commentRepository.findByVideoAndContent(video, dto.getContent());

        if (comment.isPresent()) {
            throw new AlreadyExist("akready exsists");
        }

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(dto.getContent());
        commentEntity.setVideo(video);
        commentEntity.setProfile(getProfile());
        commentRepository.save(commentEntity);

        CommentDTO response = new CommentDTO();
        response.setContent(commentEntity.getContent());
        response.setVideoId(commentEntity.getVideo().getId());
        response.setProfileId(commentEntity.getProfile().getId());
        response.setLikeCount(commentEntity.getLikeCount());
        response.setDisLikeCount(commentEntity.getDisLikeCount());
        response.setCreatedDate(commentEntity.getCreatedDate());
        return response;
    }

    public CommentDTO update(Integer id, CommentDTO dto) {

        Optional<CommentEntity> repository = commentRepository.findByIdAndVisibleTrue(id);
        if (repository.isEmpty()) {
            throw new BadRequestException("not found");
        } else if (!repository.get().getProfile().getId().equals(getProfile().getId())) {
            throw new BadRequestException("not permish");
        }
        CommentEntity commentEntity = repository.get();
        commentEntity.setContent(dto.getContent());
        commentRepository.updateContent(dto.getContent(), id);
        CommentDTO response = new CommentDTO();
        response.setContent(commentEntity.getContent());
        response.setVideoId(commentEntity.getVideo().getId());
        response.setProfileId(commentEntity.getProfile().getId());
        response.setLikeCount(commentEntity.getLikeCount());
        response.setDisLikeCount(commentEntity.getDisLikeCount());
        response.setCreatedDate(commentEntity.getCreatedDate());
        return response;
    }

    public ResposeDTO delete(Integer id) {
       /* Optional<CommentEntity> repository = commentRepository.findByIdAndVisibleTrue(id);
        if (repository.isEmpty()) {
            throw new BadRequestException("not found");
        } else if (!repository.get().getProfile().getId().equals(getProfile().getId()) || !getProfile().getRole().name().equals("ROLE_ADMIN")) {
            throw new BadRequestException("not permish");
        }*/
        checkPerMitAdmin(id);
        commentRepository.delete(id);
        return new ResposeDTO("succsess", true);
    }

    public PageImpl list(int page, int size) {
        if (!getProfile().getRole().name().equals("ROLE_ADMIN")) {
            throw new BadRequestException("not permish");
        }

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CommentEntity> all = commentRepository.findAll(pageable);

        List<CommentEntity> list = all.getContent();
        List<CommentDTO> dtoList = new LinkedList<>();

        list.forEach(commentEntity -> {
            CommentDTO response = new CommentDTO();
            response.setContent(commentEntity.getContent());
            response.setVideoId(commentEntity.getVideo().getId());
            response.setProfileId(commentEntity.getProfile().getId());
            response.setLikeCount(commentEntity.getLikeCount());
            response.setDisLikeCount(commentEntity.getDisLikeCount());
            response.setCreatedDate(commentEntity.getCreatedDate());
            dtoList.add(response);
        });

        return new PageImpl(dtoList, pageable, all.getTotalElements());
    }

    public List<CommentResponseDTO> listByProfileId(Integer id) {
        if (!getProfile().getRole().name().equals("ROLE_ADMIN")) {
            throw new BadRequestException("not permish");
        }

        List<CommentResponseRepository> repositories = commentRepository.listByProfileId1(id);
        List<CommentResponseDTO> dtoList = new LinkedList<>();
        repositories.forEach(commentResponseRepository -> {

            CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
            commentResponseDTO.setContent(commentResponseRepository.getContent());
            commentResponseDTO.setLikeCount(commentResponseRepository.getLikeCount());
            commentResponseDTO.setDisLikeCount(commentResponseRepository.getDisLikeCount());
            commentResponseDTO.setCreatedDate(commentResponseRepository.getCreatedDate());

            VideoDTO videoDTO = new VideoDTO();
            videoDTO.setId(commentResponseRepository.getVideoId());
            videoDTO.setTitle(commentResponseRepository.getVideoTitle());
            videoDTO.setPreview(new AttachDTO(commentResponseRepository.getPreviewAttachId()));
            commentResponseDTO.setVideo(videoDTO);
            dtoList.add(commentResponseDTO);

        });
        return dtoList;
    }

    public List<CommentResponseDTO> listByOwner() {

        if (!getProfile().getId().equals(getProfile().getId())) {
            throw new BadRequestException("not permish");
        }

        List<CommentResponseRepository> repositories = commentRepository.listByProfileId1(getProfile().getId());
        List<CommentResponseDTO> dtoList = new LinkedList<>();
        repositories.forEach(commentResponseRepository -> {

            CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
            commentResponseDTO.setContent(commentResponseRepository.getContent());
            commentResponseDTO.setLikeCount(commentResponseRepository.getLikeCount());
            commentResponseDTO.setDisLikeCount(commentResponseRepository.getDisLikeCount());
            commentResponseDTO.setCreatedDate(commentResponseRepository.getCreatedDate());

            VideoDTO videoDTO = new VideoDTO();
            videoDTO.setId(commentResponseRepository.getVideoId());
            videoDTO.setTitle(commentResponseRepository.getVideoTitle());
            videoDTO.setPreview(new AttachDTO(commentResponseRepository.getPreviewAttachId()));
            commentResponseDTO.setVideo(videoDTO);
            dtoList.add(commentResponseDTO);

        });
        return dtoList;

    }

    public List<CommentShortInfo> listByvideoId(String id) {

        if (!getProfile().getRole().name().equals("ROLE_ADMIN")) {
            throw new BadRequestException("not permish");
        }

        List<CommentShortInfoRepository> repositories = commentRepository.listByvideoId(id);

        List<CommentShortInfo> dto = new LinkedList<>();
        repositories.forEach(commentShortInfoRepository -> {

            CommentShortInfo shortInfo = new CommentShortInfo();
            shortInfo.setContent(commentShortInfoRepository.getContent());
            shortInfo.setLikeCount(commentShortInfoRepository.getLikeCount());
            shortInfo.setDisLikeCount(commentShortInfoRepository.getDisLikeCount());
            shortInfo.setCreatedDate(commentShortInfoRepository.getCreatedDate());

            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setId(commentShortInfoRepository.getProfileId());
            profileDTO.setName(commentShortInfoRepository.getProfileName());
            profileDTO.setSurname(commentShortInfoRepository.getProfileSurname());
            profileDTO.setPhototId(new AttachDTO(commentShortInfoRepository.getProfilePhotoId()));
            profileDTO.setUrl(serverUrl + "" + "attache/open" + commentShortInfoRepository.getProfilePhotoId());
            shortInfo.setProfile(profileDTO);

            dto.add(shortInfo);

        });

        return dto;
    }

    public List<CommentShortInfo> commentRepliedCommentByCommentId(Integer id) {

        if (!getProfile().getRole().name().equals("ROLE_ADMIN")) {
            throw new BadRequestException("not permish");
        }

        List<CommentShortInfoRepository> repositories = commentRepository.commentRepliedCommentByCommentId(id);

        List<CommentShortInfo> dto = new LinkedList<>();
        repositories.forEach(commentShortInfoRepository -> {

            CommentShortInfo shortInfo = new CommentShortInfo();
            shortInfo.setContent(commentShortInfoRepository.getContent());
            shortInfo.setLikeCount(commentShortInfoRepository.getLikeCount());
            shortInfo.setDisLikeCount(commentShortInfoRepository.getDisLikeCount());
            shortInfo.setCreatedDate(commentShortInfoRepository.getCreatedDate());

            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setId(commentShortInfoRepository.getProfileId());
            profileDTO.setName(commentShortInfoRepository.getProfileName());
            profileDTO.setSurname(commentShortInfoRepository.getProfileSurname());
            profileDTO.setPhototId(new AttachDTO(commentShortInfoRepository.getProfilePhotoId()));
            profileDTO.setUrl(serverUrl + "" + "attache/open" + commentShortInfoRepository.getProfilePhotoId());
            shortInfo.setProfile(profileDTO);


            dto.add(shortInfo);

        });

        return dto;
    }

    public boolean get(Integer comId) {

        Optional<CommentEntity> entity = commentRepository.findById(comId);
        if (entity.get().getVisible().equals(Boolean.TRUE)) {
            return true;
        }
        return false;
    }

    public ProfileEntity getProfile() {
        CustomUserDetails user = SpringSecurityUtil.getCurrentUser();
        return user.getProfile();
    }

    private CommentEntity checkPerMitAdmin(Integer id) {
        Optional<CommentEntity> entity = commentRepository.findByIdAndVisibleTrue(id);
        if (entity.isEmpty()) {
            throw new BadRequestException("not found comment");
        }


        if (!getProfile().getId().equals(getProfile().getId())) {
            if (!getProfile().getRole().name().equals("ROLE_ADMIN")) {
                throw new BadRequestException("not permish");
            }
        }

        return entity.get();
    }
}

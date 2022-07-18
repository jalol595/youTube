package com.company.youtube.service;

import com.company.youtube.config.CustomUserDetails;
import com.company.youtube.entity.CommentEntity;
import com.company.youtube.entity.CommentLikeEntity;
import com.company.youtube.entity.ProfileEntity;
import com.company.youtube.enums.CommentLikeStatus;
import com.company.youtube.exseptions.BadRequestException;
import com.company.youtube.exseptions.ItemNotFoundEseption;
import com.company.youtube.repository.CommentLikeRepository;
import com.company.youtube.repository.CommentRepository;
import com.company.youtube.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentLikeService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    @Autowired
    private CommentService commentService;

    public void commnetLike(Integer commentId) {
        likeDislike(commentId, CommentLikeStatus.LIKE);
    }

    public void commentDisLike(Integer commentId) {
        likeDislike(commentId, CommentLikeStatus.DIS_LIKE);
    }

    private void likeDislike(Integer commentId,  CommentLikeStatus status) {

        if (!commentService.get(commentId)){
            throw new BadRequestException("visible false");
        }

        Optional<CommentLikeEntity> optional = commentLikeRepository.findExists(commentId, getProfile().getId());

        if (optional.isPresent()) {
            CommentLikeEntity like = optional.get();
            like.setLikeStatus(status);
            commentLikeRepository.save(like);
            return;
        }
        boolean articleExists = commentRepository.existsById(commentId);
        if (!articleExists) {
            throw new ItemNotFoundEseption("comment NotFound");
        }

        CommentLikeEntity commentLike = new CommentLikeEntity();
        commentLike.setComment(new CommentEntity(commentId));
        commentLike.setProfile(new ProfileEntity(getProfile().getId()));
        commentLike.setLikeStatus(status);
        commentLikeRepository.save(commentLike);
    }

    public void removeLike(Integer commentId) {
        commentLikeRepository.delete(commentId, getProfile().getId());

    }

    public ProfileEntity getProfile() {
        CustomUserDetails user = SpringSecurityUtil.getCurrentUser();
        return user.getProfile();
    }
}

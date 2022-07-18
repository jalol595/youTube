package com.company.youtube.controller;



import com.company.youtube.dto.CommetLikeDTO;
import com.company.youtube.service.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/comment_like")
@RestController
public class CommentLikeController {


    @Autowired
    private CommentLikeService commentLikeService;

    @PostMapping("/like")
    public ResponseEntity<Void> like(@RequestBody CommetLikeDTO dto) {
        commentLikeService.commnetLike(dto.getCommentId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/dislike")
    public ResponseEntity<Void> dislike(@RequestBody CommetLikeDTO dto) {
        commentLikeService.commentDisLike(dto.getCommentId());
        return ResponseEntity.ok().build();
    }


    @PostMapping("/remove")
    public ResponseEntity<Void> remove(@RequestBody CommetLikeDTO dto) {
        commentLikeService.removeLike(dto.getCommentId());
        return ResponseEntity.ok().build();
    }

}

package com.company.youtube.controller;



import com.company.youtube.dto.videoLike.VideoLikeDTO;
import com.company.youtube.dto.videoLike.VideoLikeInfoDTO;
import com.company.youtube.service.VideoLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/video_like")
@RestController
public class VideoLikeController {


    @Autowired
    private VideoLikeService videoLikeService;

    @PostMapping("/like")
    public ResponseEntity<Void> like(@RequestBody VideoLikeDTO dto) {
        videoLikeService.videoLike(dto.getVideoId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/dislike")
    public ResponseEntity<Void> dislike(@RequestBody VideoLikeDTO dto) {
        videoLikeService.videoDisLike(dto.getVideoId());
        return ResponseEntity.ok().build();
    }


    @PostMapping("/remove")
    public ResponseEntity<Void> remove(@RequestBody VideoLikeDTO dto) {
        videoLikeService.removeLike(dto.getVideoId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/userLikedVideoList")
    public ResponseEntity<  List<VideoLikeInfoDTO>> userLikedVideoList() {
       List<VideoLikeInfoDTO> response= videoLikeService.userLikedVideoList();
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/getUserLikedVideoListByUserId/{id}")
    public ResponseEntity<  List<VideoLikeInfoDTO>> getUserLikedVideoListByUserId(@PathVariable("id") Integer id) {
        List<VideoLikeInfoDTO> response= videoLikeService.getUserLikedVideoListByUserId(id);
        return ResponseEntity.ok().body(response);
    }
}

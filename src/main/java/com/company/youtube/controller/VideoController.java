package com.company.youtube.controller;

import com.company.youtube.dto.response.ResposeDTO;
import com.company.youtube.dto.video.*;
import com.company.youtube.mapper.video.VideoFullInfo;
import com.company.youtube.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/create")
    public ResponseEntity<VideoDTO> create(@RequestBody @Valid CreateVideoDTO dto) {
        VideoDTO response = videoService.create(dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResposeDTO> update(@PathVariable("id") String id, @Valid @RequestBody UpdateVideoDTO dto) {
        ResposeDTO response = videoService.update(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/photo/{id}")
    public ResponseEntity<ResposeDTO> updatePhoto(@PathVariable("id") String id, @Valid @RequestBody UpdateVideoPhotoDTO dto) {
        ResposeDTO response = videoService.updatePhoto(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/baner/{id}")
    public ResponseEntity<ResposeDTO> updateBaner(@PathVariable("id") String id, @Valid @RequestBody UpdateVideoPhotoDTO dto) {
        ResposeDTO response = videoService.updateBaner(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<ResposeDTO> changeStatus(@PathVariable("id") String id, @Valid @RequestBody UpdateStatusPhotoDTO dto) {
        ResposeDTO response = videoService.changeStatus(id, dto);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/increaseViewCountbyKey/{key}")
    public ResponseEntity<?> increaseArticleViewCountbyArticleId(@PathVariable("key") String key) {
        videoService.increaseViewCountbyKey(key);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/getVideoPaginationbyCategoryId/{id}")
    public ResponseEntity<?> getVideoPaginationbyCategoryId(@PathVariable("id") Integer id) {
        List<VideoShortInfoDTO> infoDTOList = videoService.getVideoPaginationbyCategoryId(id);
        return ResponseEntity.ok().body(infoDTOList);
    }

    @GetMapping("/videoByTitle/{title}")
    public ResponseEntity<?> videoByTitle(@PathVariable("title") String id) {
        List<VideoShortInfoDTO>  infoDTOList = videoService.videoByTitle(id);
        return ResponseEntity.ok().body(infoDTOList);
    }


    @GetMapping("/videoByTagIdWithPagination/{tadId}")
    public ResponseEntity<?> videoByTitle(@PathVariable("tadId") Integer id) {
        List<VideoShortInfoDTO>  infoDTOList = videoService.videoByTagIdWithPagination(id);
        return ResponseEntity.ok().body(infoDTOList);
    }

    @GetMapping("/getVideoId/{videoId}")
    public ResponseEntity<?> videoById(@PathVariable("videoId") String id) {
       VideoFullInfoDTO  infoDTOList = videoService.getById(id);
        return ResponseEntity.ok().body(infoDTOList);
    }
}

package com.company.youtube.controller;


import com.company.youtube.dto.VideoTagDTO;
import com.company.youtube.dto.VideoTagResponseDTO;
import com.company.youtube.dto.response.ResposeDTO;
import com.company.youtube.dto.video.VideoDTO;
import com.company.youtube.service.VideoTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/video_tag")
@RestController
public class VideoTagController {

    @Autowired
    private VideoTagService videoTagService;

    @PostMapping("/create")
    public ResponseEntity<ResposeDTO> create(@RequestBody @Valid VideoTagDTO dto) {
        ResposeDTO response = videoTagService.createVideoTag(dto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResposeDTO> delete(@PathVariable("id") Integer id) {
        ResposeDTO response = videoTagService.delete(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/videoTagListByVideoId/{videoId}")
    public ResponseEntity<List<VideoTagResponseDTO>> videoTagListByVideoId(@PathVariable("videoId") String videoId) {
        List<VideoTagResponseDTO> responseDTO = videoTagService.videoTagListByVideoId(videoId);
        return ResponseEntity.ok().body(responseDTO);
    }


}

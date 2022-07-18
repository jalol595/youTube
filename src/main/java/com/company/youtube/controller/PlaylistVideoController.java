package com.company.youtube.controller;


import com.company.youtube.dto.playlistVideo.PlaylistVideoDTO;
import com.company.youtube.dto.playlistVideo.PlaylistVideoShortInfoDTO;
import com.company.youtube.dto.playlistVideo.ResponsePlaylistVideoDTO;
import com.company.youtube.dto.playlistVideo.UpdatePlaylistVideoDTO;
import com.company.youtube.dto.response.ResposeDTO;
import com.company.youtube.service.PlaylistVideoService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/playlist_video")
public class PlaylistVideoController {

    @Autowired
    private PlaylistVideoService playlistVideoService;

    @PostMapping("/create")
    public ResponseEntity<ResponsePlaylistVideoDTO> create(@RequestBody  PlaylistVideoDTO dto) {
        ResponsePlaylistVideoDTO response = playlistVideoService.create(dto);
        return ResponseEntity.ok().body(response);
    }

    //xato ishlayapti
    @PutMapping("/update/{id}")
    public ResponseEntity<ResposeDTO> update(@PathVariable("id") Integer id,
                                                           @RequestBody  PlaylistVideoDTO dto) {
        ResposeDTO response = playlistVideoService.update(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResposeDTO> delete(@PathVariable("id") Integer id, @RequestBody  PlaylistVideoDTO dto) {
        ResposeDTO response = playlistVideoService.delete(id, dto);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/getVideolistByplayListId/{id}")
    public ResponseEntity<List<PlaylistVideoShortInfoDTO>> delete(@PathVariable("id") Integer id) {
        List<PlaylistVideoShortInfoDTO> response = playlistVideoService.getVideolistByplayListId(id);
        return ResponseEntity.ok().body(response);
    }
}

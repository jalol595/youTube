package com.company.youtube.controller;

import com.company.youtube.dto.channel.ChannelDTO;
import com.company.youtube.dto.channel.UpdateChannelDTO;
import com.company.youtube.dto.playlist.*;
import com.company.youtube.dto.response.ResposeDTO;
import com.company.youtube.service.ChannelService;
import com.company.youtube.service.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlayListController {


    @Autowired
    @Lazy
    private PlayListService playListService;

    @PostMapping("/create")
    public ResponseEntity<PlaylistDTO> create(@RequestBody @Valid PlaylistDTO dto) {
        PlaylistDTO response = playListService.create(dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResposeDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody UpdatePlayListDTO dto) {
        ResposeDTO response = playListService.update(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<ResposeDTO> changeStatus(@PathVariable("id") Integer id, @Valid @RequestBody ChangeStatusPlayListDTO dto) {
        ResposeDTO response = playListService.changeStatus(id, dto);
        return ResponseEntity.ok().body(response);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResposeDTO> delete(@PathVariable("id") Integer id) {
        ResposeDTO response = playListService.delete(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<PageImpl> getPagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "size", defaultValue = "3") int size) {

        PageImpl response = playListService.pagination(page, size);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/listByUserId/{id}")
    public ResponseEntity<List<PlaylistInfoDTO>> listByUserId(@PathVariable("id") Integer id) {
        List<PlaylistInfoDTO> response = playListService.listByUserId(id);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/getUserPlaylist")
    public ResponseEntity<List<PlaylistDTO>> getUserPlaylist() {
        List<PlaylistDTO> response = playListService.getProfilePLayList();
        return ResponseEntity.ok().body(response);
    }



    @GetMapping("/playListByChannelKey/{key}")
    public ResponseEntity<List<PlaylistDTO>> playListByChannelKey(@PathVariable("key") String key) {
        List<PlaylistDTO> response = playListService.playListByChannelKey(key);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/fullInfo/{playlistId}")
    public ResponseEntity<?> playlistByPlaylistId(@PathVariable("playlistId") Integer playlistId){
        PlaylistShortInfoDTO full = playListService.getById(playlistId);
        return ResponseEntity.ok(full);
    }
}
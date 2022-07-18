package com.company.youtube.controller;

import com.company.youtube.dto.channel.ChannelDTO;
import com.company.youtube.dto.channel.UpdateChannelDTO;
import com.company.youtube.dto.channel.UpdateChannelPhotoDTO;
import com.company.youtube.dto.response.ResposeDTO;
import com.company.youtube.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/channel")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @PostMapping("/create")
    public ResponseEntity<ChannelDTO> create(@RequestBody @Valid ChannelDTO dto){
        ChannelDTO response = channelService.create(dto);
        return ResponseEntity.ok().body(response);
    }

  @PutMapping("/update/{id}")
    public ResponseEntity<ChannelDTO> update(@PathVariable("id") String id, @Valid @RequestBody UpdateChannelDTO dto){
      ChannelDTO response = channelService.update(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/photo/{id}")
    public ResponseEntity<ResposeDTO> updatePhoto(@PathVariable("id") String id, @Valid @RequestBody UpdateChannelPhotoDTO dto){
        ResposeDTO response = channelService.updatePhoto(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/baner/{id}")
    public ResponseEntity<ResposeDTO> updateBaner(@PathVariable("id") String id, @Valid @RequestBody UpdateChannelPhotoDTO dto){
        ResposeDTO response = channelService.updateBaner(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<PageImpl> getPagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "size", defaultValue = "3") int size){

        PageImpl response = channelService.pagination(page, size);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ChannelDTO> getById(@PathVariable("id") String id){

        ChannelDTO response = channelService.getById(id);
        return ResponseEntity.ok().body(response);
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResposeDTO> delete(@PathVariable("id") String id){
        ResposeDTO response = channelService.delete(id);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/list")
    public ResponseEntity<List<ChannelDTO>> getProfileChannelList() {
        List<ChannelDTO> list = channelService.getList();
        return ResponseEntity.ok().body(list);
    }
}

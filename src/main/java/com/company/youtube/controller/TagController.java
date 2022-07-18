package com.company.youtube.controller;

import com.company.youtube.dto.TagDTO;
import com.company.youtube.dto.response.ResposeDTO;
import com.company.youtube.service.CategoryService;
import com.company.youtube.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;


    @PostMapping("/create")
    public ResponseEntity<TagDTO> create(@RequestBody @Valid TagDTO dto){
       TagDTO response = tagService.create(dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TagDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody TagDTO dto){
        TagDTO response = tagService.update(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResposeDTO> delete(@PathVariable("id") Integer id){
        ResposeDTO response = tagService.delete(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<TagDTO>> getProfileList() {
        List<TagDTO> list = tagService.getList();
        return ResponseEntity.ok().body(list);
    }
}

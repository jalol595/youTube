package com.company.youtube.controller;

import com.company.youtube.dto.CategoryDTO;
import com.company.youtube.dto.response.ResposeDTO;
import com.company.youtube.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> create(@RequestBody @Valid CategoryDTO dto){
       CategoryDTO response = categoryService.create(dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody CategoryDTO dto){
        CategoryDTO response = categoryService.update(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResposeDTO> delete(@PathVariable("id") Integer id){
        ResposeDTO response = categoryService.delete(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CategoryDTO>> getProfileList() {
        List<CategoryDTO> list = categoryService.getList();
        return ResponseEntity.ok().body(list);
    }
}

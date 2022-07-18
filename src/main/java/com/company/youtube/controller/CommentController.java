package com.company.youtube.controller;

import com.company.youtube.dto.comment.CommentDTO;
import com.company.youtube.dto.comment.CommentResponseDTO;
import com.company.youtube.dto.comment.CommentShortInfo;
import com.company.youtube.dto.response.ResposeDTO;
import com.company.youtube.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RequestMapping("/comment")
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentDTO> create(@RequestBody @Valid CommentDTO dto) {
        CommentDTO commentDTO = commentService.create(dto);
        return ResponseEntity.ok().body(commentDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CommentDTO> update(@PathVariable("id") Integer id, @RequestBody  CommentDTO dto) {
        CommentDTO commentDTO = commentService.update(id, dto);
        return ResponseEntity.ok().body(commentDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResposeDTO> delete(@PathVariable("id") Integer id) {
        ResposeDTO respose = commentService.delete(id);
        return ResponseEntity.ok().body(respose);
    }

    @GetMapping("/list")
    public ResponseEntity<PageImpl> list(@RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "size", defaultValue = "3") int size){
        PageImpl response = commentService.list(page, size);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/listByProfileId/{id}")
    public ResponseEntity<List<CommentResponseDTO>> listByProfileId(@PathVariable("id") Integer id){
        List<CommentResponseDTO> response = commentService.listByProfileId(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/listByOwner")
    public ResponseEntity<List<CommentResponseDTO>> listByOwner(){
        List<CommentResponseDTO> response = commentService.listByOwner();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/listByvideoId/{id}")
    public ResponseEntity<List<CommentShortInfo>> listByvideoId(@PathVariable("id") String id){
        List<CommentShortInfo> response = commentService.listByvideoId(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/commentRepliedCommentByCommentId/{commentId}")
    public ResponseEntity<List<CommentShortInfo>> commentRepliedCommentByCommentId(@PathVariable("commentId") Integer commentId){
        List<CommentShortInfo> response = commentService.commentRepliedCommentByCommentId(commentId);
        return ResponseEntity.ok().body(response);
    }



}

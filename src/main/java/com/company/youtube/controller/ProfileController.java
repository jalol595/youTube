package com.company.youtube.controller;

import com.company.youtube.dto.profile.ChangePassworDTO;
import com.company.youtube.dto.profile.CreateProfileDTO;
import com.company.youtube.dto.profile.ProfileDTO;
import com.company.youtube.dto.profile.UpdateDetailDTO;
import com.company.youtube.dto.response.ResposeDTO;
import com.company.youtube.dto.attach.UpdateAttachDTO;
import com.company.youtube.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/changePassword")
    public ResponseEntity<ResposeDTO> changePassword(@RequestBody @Valid ChangePassworDTO dto) {
        ResposeDTO resposeDTO = profileService.changePassword(dto);
        return ResponseEntity.ok(resposeDTO);
    }

//qilinamdi
//    @PostMapping("/updateEmail")
//    public ResponseEntity<String> updateEmail(@RequestBody ProfileDTO dto) {
//        String respose = profileService.updateEmail(dto);
//        return ResponseEntity.ok(respose);
//    }

    //qilinamdi
    @GetMapping("/getById/{email}/{id}")
    public ResponseEntity<ResposeDTO> getById(@PathVariable("email, id") String email, Integer id) {
        ResposeDTO resposeDTO = profileService.getById(email, id);
        return ResponseEntity.ok(resposeDTO);
    }

    @PutMapping("/updateDetail")
    public HttpEntity<ResposeDTO> updateDetail(@RequestBody @Valid UpdateDetailDTO dto) {
        ResposeDTO detail = profileService.updateDetail(dto);
        return ResponseEntity.badRequest().body(detail);
    }

    @PutMapping("/updatePhoto")
    public ResponseEntity<ResposeDTO> updatePhoto(@RequestBody UpdateAttachDTO dto) {
        ResposeDTO detail = profileService.updatePhoto(dto);
        return ResponseEntity.ok(detail);
    }


    @GetMapping("/getDetail")
    public ResponseEntity<ProfileDTO> getDetail() {
        ProfileDTO resposeDTO = profileService.getDetail();
        return ResponseEntity.ok(resposeDTO);
    }

    @PostMapping("/adm/create")
    public ResponseEntity<ProfileDTO> create(@RequestBody @Valid CreateProfileDTO dto) {
        ProfileDTO resposeDTO = profileService.create(dto);
        return ResponseEntity.ok(resposeDTO);
    }

}

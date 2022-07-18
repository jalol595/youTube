package com.company.youtube.controller;

import com.company.youtube.dto.profile.RegisterDTO;
import com.company.youtube.service.RegisterService;
import com.company.youtube.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/register")
@RestController
public class RegistirationController {


    @Autowired
    private RegisterService registerService;


    @PostMapping("/user")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO dto){
        String register = registerService.register(dto);
        return ResponseEntity.ok().body(register);
    }



    @GetMapping("/email/verification/{token}")
    public ResponseEntity<?> login(@PathVariable("token") String token) {
        Integer PID = JwtUtil.decode(token);
        String s= registerService.verificationEmail(PID);
        return ResponseEntity.ok(s);
    }
}

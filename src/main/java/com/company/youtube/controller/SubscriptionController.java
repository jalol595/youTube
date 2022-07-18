package com.company.youtube.controller;


import com.company.youtube.dto.subscraption.SubscriptionDTO;
import com.company.youtube.dto.subscraption.SubscriptionResponseDTO;
import com.company.youtube.dto.response.ResposeDTO;
import com.company.youtube.dto.subscraption.SubscriptionShortInfoDTO;
import com.company.youtube.dto.subscraption.UpdateNotififSubscriptionDTO;
import com.company.youtube.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;


    @PostMapping("/create")
    public ResponseEntity<SubscriptionResponseDTO> create(@RequestBody @Valid SubscriptionDTO dto) {
        SubscriptionResponseDTO response = subscriptionService.create(dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<ResposeDTO> changeStatus(@PathVariable("id") Integer id, @RequestBody SubscriptionDTO dto) {
        ResposeDTO response = subscriptionService.changeStatus(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/changeNotification/{id}")
    public ResponseEntity<ResposeDTO> changeNotification(@PathVariable("id") Integer id,  @RequestBody UpdateNotififSubscriptionDTO dto) {
        ResposeDTO response = subscriptionService.changeNotification(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/userSubscriptionList")
    public ResponseEntity<List<SubscriptionShortInfoDTO>> userSubscriptionList() {
        List<SubscriptionShortInfoDTO> response = subscriptionService.userSubscriptionList();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/userSubscriptionListByUserId/{id}")
    public ResponseEntity<List<SubscriptionShortInfoDTO>> userSubscriptionListByUserId(@PathVariable("id") Integer id) {
        List<SubscriptionShortInfoDTO> response = subscriptionService.userSubscriptionListByUserId(id);
        return ResponseEntity.ok().body(response);
    }

}

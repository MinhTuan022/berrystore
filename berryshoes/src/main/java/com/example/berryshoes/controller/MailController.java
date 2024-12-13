package com.example.berryshoes.controller;

import com.example.berryshoes.dto.request.MailRequest;
import com.example.berryshoes.utils.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMail(@RequestBody MailRequest mailRequest) {
        mailService.sendMail(mailRequest.getTo(), mailRequest.getTitle(), mailRequest.getContent());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

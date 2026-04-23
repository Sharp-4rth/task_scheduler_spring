package com.scheduler.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Void> redirect() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "/swagger-ui/index.html")
                .build();
    }
}

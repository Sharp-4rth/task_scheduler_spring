package com.scheduler.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/api")
    public ResponseEntity<Void> redirectToSwaggerUI(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/swagger-ui/index.html");
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}

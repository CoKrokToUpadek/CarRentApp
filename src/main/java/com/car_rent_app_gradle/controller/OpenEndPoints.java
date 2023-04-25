package com.car_rent_app_gradle.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/")
@AllArgsConstructor
public class OpenEndPoints {

    @GetMapping("/hello")//for testing purposes
    public ResponseEntity<String> getTestMessage(){
        return ResponseEntity.ok("im deployed and working");
    }
}

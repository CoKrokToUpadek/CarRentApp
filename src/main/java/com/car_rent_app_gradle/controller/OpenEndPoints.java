package com.car_rent_app_gradle.controller;


import com.car_rent_app_gradle.domain.dto.VehicleForCustomersDto;
import com.car_rent_app_gradle.domain.dto.CustomerDto;
import com.car_rent_app_gradle.service.OpenEndPointsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/")
public class OpenEndPoints {

    OpenEndPointsService openEndPointsService;
    @Autowired
    public OpenEndPoints(OpenEndPointsService openEndPointsService) {
        this.openEndPointsService = openEndPointsService;
    }

    @PostMapping("/createAccount")
    public ResponseEntity<String> createAccount(@RequestBody CustomerDto customerDto){
        return ResponseEntity.ok("im deployed and working");
    }

    @GetMapping("/loginAsCustomer")
    public ResponseEntity<String> loginAsCustomer(Authentication authentication){
        return ResponseEntity.ok(openEndPointsService.generateTokenForLogin(authentication));
    }
    @GetMapping("/loginAsEmployee")
    public ResponseEntity<String> loginAsEmployee(Authentication authentication){
        return ResponseEntity.ok(openEndPointsService.generateTokenForLogin(authentication));
    }

    @GetMapping("/getVehicleList")
    public ResponseEntity<List<VehicleForCustomersDto>> getVehicleList(){
        List<VehicleForCustomersDto> cars=new ArrayList<>();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @GetMapping("/hello")//for testing purposes
    public ResponseEntity<String> getTestMessage(){
        return ResponseEntity.ok("hello from open endpoint");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE')")
    @GetMapping("/helloAuthorized")//for testing purposes
    public ResponseEntity<String> getTestMessageAuthorized(){
        return ResponseEntity.ok("hello from closed endpoint");
    }

}

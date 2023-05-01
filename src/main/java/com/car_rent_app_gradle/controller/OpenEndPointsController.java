package com.car_rent_app_gradle.controller;


import com.car_rent_app_gradle.domain.dto.TokenAndRoleDto;
import com.car_rent_app_gradle.domain.dto.VehicleForCustomersDto;
import com.car_rent_app_gradle.domain.dto.CustomerDto;
import com.car_rent_app_gradle.service.OpenEndPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/")
public class OpenEndPointsController {

    OpenEndPointsService openEndPointsService;

    @Autowired
    public OpenEndPointsController(OpenEndPointsService openEndPointsService) {
        this.openEndPointsService = openEndPointsService;
    }

    @PostMapping("/createAccount")
    public ResponseEntity<String> createAccount(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok("im deployed and working");
    }

    @GetMapping("/login")
    public ResponseEntity<TokenAndRoleDto> login(Authentication authentication) {
        return ResponseEntity.ok(openEndPointsService.generateToken(authentication));
    }

    @GetMapping("/getVehicleList")
    public ResponseEntity<List<VehicleForCustomersDto>> getVehicleList() {
        List<VehicleForCustomersDto> cars = new ArrayList<>();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    //works
    @GetMapping("/hello")//for testing purposes
    public ResponseEntity<String> getTestMessage() {
        return ResponseEntity.ok("hello from open endpoint");
    }

    //works
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE')")
    @GetMapping("/helloAuthorized")//for testing purposes
    public ResponseEntity<String> getTestMessageAuthorized() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        System.out.println(request.getHeader("Authorization"));
        return ResponseEntity.ok("hello from closed endpoint");
    }

}

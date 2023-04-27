package com.car_rent_app_gradle.controller;


import com.car_rent_app_gradle.domain.dto.VehicleForCustomersDto;
import com.car_rent_app_gradle.domain.dto.CustomerDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/")
@AllArgsConstructor
public class OpenEndPoints {

    @PostMapping("/createAccount")
    public ResponseEntity<String> createAccount(@RequestBody CustomerDto customerDto){
        return ResponseEntity.ok("im deployed and working");
    }

    @GetMapping("/loginAsCustomer")
    public ResponseEntity<String> loginAsCustomer(@RequestParam String login,@RequestParam String password){
        return ResponseEntity.ok("im deployed and working");
    }

    @GetMapping("/loginAsEmployee")
    public ResponseEntity<String> loginAsEmployee(@RequestParam String login,@RequestParam String password){
        return ResponseEntity.ok("im deployed and working");
    }

    @GetMapping("/getCarList")
    public ResponseEntity<List<VehicleForCustomersDto>> getVehicleList(@RequestParam String carBrandName){
        List<VehicleForCustomersDto> cars=new ArrayList<>();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }
    @GetMapping("/hello")//for testing purposes
    public ResponseEntity<String> getTestMessage(){
        return ResponseEntity.ok("im deployed and working");
    }

}

package com.car_rent_app_gradle.controller;


import com.car_rent_app_gradle.domain.dto.CustomerAccountDto;
import com.car_rent_app_gradle.domain.dto.TokenAndRoleDto;
import com.car_rent_app_gradle.domain.dto.VehicleForCustomersDto;
import com.car_rent_app_gradle.errorhandlers.EmptyAuthenticationException;
import com.car_rent_app_gradle.errorhandlers.VehicleListIsEmptyException;
import com.car_rent_app_gradle.service.OpenEndPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/")
public class OpenEndPointsController {

  private final OpenEndPointsService openEndPointsService;

    @Autowired
    public OpenEndPointsController(OpenEndPointsService openEndPointsService) {
        this.openEndPointsService = openEndPointsService;
    }

    @PostMapping("/createCustomerAccount")//ok
    public ResponseEntity<String> createAccount(@RequestBody CustomerAccountDto customerAccountCreationDto) {
        return ResponseEntity.ok(openEndPointsService.createCustomerAccount(customerAccountCreationDto));
    }

    @GetMapping("/login")  //ok
    public ResponseEntity<TokenAndRoleDto> login(Authentication authentication) throws EmptyAuthenticationException {
        return ResponseEntity.ok(openEndPointsService.generateToken(authentication));
    }

    @GetMapping("/getVehicleListForClients")//ok
    public ResponseEntity<List<VehicleForCustomersDto>> getVehicleListForClients() throws VehicleListIsEmptyException {
        return ResponseEntity.ok(openEndPointsService.getVehicleListForClients());
    }

    @GetMapping("/hello")//for testing purposes   //ok
    public ResponseEntity<String> getTestMessage() {
        return ResponseEntity.ok("hello from open endpoint");
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE')")//ok
    @GetMapping("/helloAuthorized")//for testing purposes
    public ResponseEntity<String> getTestMessageAuthorized() {
        return ResponseEntity.ok("hello from closed endpoint");
    }

}

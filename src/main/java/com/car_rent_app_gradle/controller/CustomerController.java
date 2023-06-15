package com.car_rent_app_gradle.controller;

import com.car_rent_app_gradle.domain.dto.CustomerDto;
import com.car_rent_app_gradle.domain.dto.ReservationDto;
import com.car_rent_app_gradle.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

    CustomerService customerService;

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER')")
    @PostMapping("/rent-new-vehicle")
    public ResponseEntity<String> rentNewVehicle(@CurrentSecurityContext SecurityContext context,@RequestParam Long carId){
        return ResponseEntity.ok("ok mk");
    }
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER')")//ok
    @PutMapping("/change-personal-information")
    public ResponseEntity<String> changePersonalInformation(@CurrentSecurityContext SecurityContext context, @RequestBody CustomerDto customerDto){
        return new ResponseEntity(customerService.changePersonalInformation(context.getAuthentication().getName(),customerDto), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER')")
    @PutMapping("/cancel-vehicle-reservation")
    public ResponseEntity<String> cancelVehicleReservation(@CurrentSecurityContext SecurityContext context,@RequestParam Long carId){//need security context
        return ResponseEntity.ok("ok mk");
    }
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER')")
    @DeleteMapping("/get-all-my-Reservations")
    public ResponseEntity<List<ReservationDto>> getVehicleReservationList(@CurrentSecurityContext SecurityContext context){//need security context
        List<ReservationDto> reservationsList=new ArrayList<>();
        return new ResponseEntity(reservationsList, HttpStatus.OK);
    }
}

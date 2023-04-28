package com.car_rent_app_gradle.controller;

import com.car_rent_app_gradle.domain.dto.ReservationDto;
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE','ROLE_CUSTOMER')")
    @PostMapping("/rentNewVehicle")
    public ResponseEntity<String> rentNewVehicle(@CurrentSecurityContext SecurityContext context,@RequestParam Long carId){
        return ResponseEntity.ok("ok mk");
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE','ROLE_CUSTOMER')")
    @PutMapping("/changePersonalInformation")
    public ResponseEntity<String> changePersonalInformation(@CurrentSecurityContext SecurityContext context){//need security context
        return ResponseEntity.ok("ok mk");
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE','ROLE_CUSTOMER')")
    @PutMapping("/cancelVehicleReservation")
    public ResponseEntity<String> cancelVehicleReservation(@CurrentSecurityContext SecurityContext context,@RequestParam Long carId){//need security context
        return ResponseEntity.ok("ok mk");
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE','ROLE_CUSTOMER')")
    @DeleteMapping("/getAllMyReservations")
    public ResponseEntity<List<ReservationDto>> getVehicleReservationList(@CurrentSecurityContext SecurityContext context,@RequestParam Long carId){//need security context
        List<ReservationDto> reservationsList=new ArrayList<>();
        return new ResponseEntity(reservationsList, HttpStatus.OK);
    }
}

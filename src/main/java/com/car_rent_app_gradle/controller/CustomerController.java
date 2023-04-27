package com.car_rent_app_gradle.controller;

import com.car_rent_app_gradle.domain.dto.ReservationDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/rentNewCar")
    public ResponseEntity<String> rentNewVehicle(@CurrentSecurityContext SecurityContext context,@RequestParam Long carId){
        return ResponseEntity.ok("ok mk");
    }

    @PutMapping("/changePersonalInformation")
    public ResponseEntity<String> changePersonalInformation(@CurrentSecurityContext SecurityContext context){//need security context
        return ResponseEntity.ok("ok mk");
    }

    @PutMapping("/cancelCarReservation")
    public ResponseEntity<String> cancelVehicleReservation(@CurrentSecurityContext SecurityContext context,@RequestParam Long carId){//need security context
        return ResponseEntity.ok("ok mk");
    }

    @DeleteMapping("/getAllMyReservations")
    public ResponseEntity<List<ReservationDto>> getVehicleReservationList(@CurrentSecurityContext SecurityContext context,@RequestParam Long carId){//need security context
        List<ReservationDto> reservationsList=new ArrayList<>();
        return new ResponseEntity(reservationsList, HttpStatus.OK);
    }
}

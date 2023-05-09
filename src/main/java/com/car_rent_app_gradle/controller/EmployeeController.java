package com.car_rent_app_gradle.controller;

import com.car_rent_app_gradle.domain.dto.*;
import com.car_rent_app_gradle.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin("*")
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE')")
    @PostMapping("/addNewVehicle")
    public ResponseEntity<String> addNewVehicle(@CurrentSecurityContext SecurityContext context, @RequestBody VehicleForCustomersDto carDto){
        return ResponseEntity.ok("ok mk");
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE')")
    @DeleteMapping("/removeVehicle")
    public ResponseEntity<String> removeVehicle(@CurrentSecurityContext SecurityContext context,@RequestParam Long carId){
        return ResponseEntity.ok("ok mk");
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE')")
    @DeleteMapping("/editVehicle")
    public ResponseEntity<String> editVehicle(@CurrentSecurityContext SecurityContext context,@RequestParam Long carId){
        return ResponseEntity.ok("ok mk");
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE')")
    @GetMapping("/getReservationManagedByMe")
    public ResponseEntity<String> getReservationManagedByMe(@CurrentSecurityContext SecurityContext context){
        return ResponseEntity.ok("ok mk");
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/getAllReservations")
    public ResponseEntity<List<ReservationDto>> getAllReservations(){
        List<ReservationDto> reservationDtoList=new ArrayList<>();
        return new ResponseEntity(reservationDtoList,HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")//ok
    @PostMapping("/addEmployee")
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeAccountCreationDto employeeAccountCreationDto){
        return ResponseEntity.ok(employeeService.addEmployee(employeeAccountCreationDto));
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/editEmployee")
    public ResponseEntity<String> editEmployee(@RequestParam Long employeeId){
        return ResponseEntity.ok("ok mk");
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")//TODO add tests
    @GetMapping("/getEmployeeList")
    public ResponseEntity<List<EmployeeDto>> getEmployeeList(){
        return new  ResponseEntity(employeeService.getEmployeeList(), HttpStatus.OK);
    }

    //some sort of overwatch that would inform employee that manages transaction that transaction was canceled

}

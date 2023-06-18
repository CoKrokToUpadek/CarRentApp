package com.car_rent_app_gradle.controller;

import com.car_rent_app_gradle.domain.dto.*;
import com.car_rent_app_gradle.errorhandlers.EmployeeDbEmptyException;
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


@RestController
@CrossOrigin("*")
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE')")//ok
    @PostMapping("/add-new-vehicle")
    public ResponseEntity<String> addNewVehicle(@CurrentSecurityContext SecurityContext context, @RequestBody VehicleForEmployeesDto carDto){
        return new ResponseEntity(employeeService.addNewVehicle(carDto,context),HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE')")//TODO vehicle can only be removed if it was never rented to keep db integrity
    @DeleteMapping("/remove-vehicle")
    public ResponseEntity<String> removeVehicle(@CurrentSecurityContext SecurityContext context,@RequestParam Long carId){
        return ResponseEntity.ok("ok mk");
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE')")
    @DeleteMapping("/edit-vehicle")
    public ResponseEntity<String> editVehicle(@CurrentSecurityContext SecurityContext context,@RequestParam Long carId){
        return ResponseEntity.ok("ok mk");
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE')")
    @GetMapping("/get-reservations-managed-by-me")
    public ResponseEntity<String> getReservationManagedByMe(@CurrentSecurityContext SecurityContext context){
        return ResponseEntity.ok("ok mk");
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/get-all-reservations")
    public ResponseEntity<List<ReservationDto>> getAllReservations(){
        List<ReservationDto> reservationDtoList=new ArrayList<>();
        return new ResponseEntity(reservationDtoList,HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")//ok
    @PostMapping("/add-employee")
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeAccountDto employeeAccountCreationDto){
        return ResponseEntity.ok(employeeService.addEmployee(employeeAccountCreationDto));
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/edit-employee")
    public ResponseEntity<String> editEmployee(@RequestParam Long employeeId){
        return ResponseEntity.ok("ok mk");
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")//ok
    @GetMapping("/get-employee-list")
    public ResponseEntity<List<EmployeeDto>> getEmployeeList() throws EmployeeDbEmptyException {
        return new  ResponseEntity(employeeService.getEmployeeList(), HttpStatus.OK);
    }

    //TODO some sort of over-watch that would inform employee that manages transaction that transaction was canceled

}

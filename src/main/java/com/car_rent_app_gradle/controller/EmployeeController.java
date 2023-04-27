package com.car_rent_app_gradle.controller;

import com.car_rent_app_gradle.domain.dto.CarDto;
import com.car_rent_app_gradle.domain.dto.EmployeeDto;
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
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {

    @PostMapping("/addNewCar")
    public ResponseEntity<String> addNewVehicle(@CurrentSecurityContext SecurityContext context, @RequestBody CarDto carDto){
        return ResponseEntity.ok("ok mk");
    }

    @DeleteMapping("/removeCar")
    public ResponseEntity<String> removeVehicle(@CurrentSecurityContext SecurityContext context,@RequestParam Long carId){
        return ResponseEntity.ok("ok mk");
    }

    @DeleteMapping("/editCar")
    public ResponseEntity<String> editVehicle(@CurrentSecurityContext SecurityContext context,@RequestParam Long carId){
        return ResponseEntity.ok("ok mk");
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeDto employeeDto){
        return ResponseEntity.ok("ok mk");
    }

    @PutMapping("/editEmployee")
    public ResponseEntity<String> editEmployee(@RequestParam Long employeeId){
        return ResponseEntity.ok("ok mk");
    }

    @PutMapping("/getEmployeeList")
    public ResponseEntity<List<EmployeeDto>> getEmployeeList(){
        List<EmployeeDto> employeeDtoList=new ArrayList<>();
        return new  ResponseEntity(employeeDtoList, HttpStatus.OK);
    }

    //some sort of overwatch that would inform employee that manages transaction that transaction was canceled

}

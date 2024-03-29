package com.car_rent_app_gradle.errorhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


//by implementing exceptions this way, I have both return msgs for front, and also internal msgs for logic in code
@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EmptyAuthenticationException.class)
    public ResponseEntity<Object> emptyAuthenticationExceptionHandler(EmptyAuthenticationException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ApplicationDataBaseException.class)
    public ResponseEntity<Object> applicationDataBaseExceptionHandler(ApplicationDataBaseException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(VehicleListIsEmptyException.class)
    public ResponseEntity<Object> vehicleListIsEmptyExceptionHandler(VehicleListIsEmptyException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK); /*made so that front can do some sneaky redirect
        instead of printing empty list*/
    }

    @ExceptionHandler(EmployeeDbEmptyException.class)
    public ResponseEntity<Object> EmployeeDbEmptyExceptionHandler(EmployeeDbEmptyException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK);
    }

}

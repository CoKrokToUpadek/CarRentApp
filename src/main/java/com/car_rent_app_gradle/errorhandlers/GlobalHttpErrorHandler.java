package com.car_rent_app_gradle.errorhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(LoginAsEmployeeInCustomerSectionException.class)
    public ResponseEntity<Object> handleInvalidEmployeeLogin(LoginAsEmployeeInCustomerSectionException exception){
        return new ResponseEntity<>("Employee login credentials in customer panel", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(LoginAsCustomerInEmployeeSectionException.class)
    public ResponseEntity<Object> handleInvalidCustomerLogin(LoginAsCustomerInEmployeeSectionException exception){
        return new ResponseEntity<>("Beer Db is empty", HttpStatus.FORBIDDEN);
    }


}

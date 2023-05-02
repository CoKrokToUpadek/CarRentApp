package com.car_rent_app_gradle.errorhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EmptyAuthenticationException.class)
    public ResponseEntity<Object> emptyAuthenticationExceptionHandler(EmptyAuthenticationException exception){
        return new ResponseEntity<>("Authentication data missing", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ApplicationDataBaseException.class)
    public ResponseEntity<Object> applicationDataBaseExceptionHandler(ApplicationDataBaseException exception){
        return new ResponseEntity<>("Error while fetching newly created user", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

package com.car_rent_app_gradle.errorhandlers;

public class EmployeeDbEmptyException extends Exception{
    public EmployeeDbEmptyException() {super(AppUserCreationExceptionAndValidationEnum.EMPLOYEE_DB_EMPTY_EXCEPTION.getValue());
    }
}

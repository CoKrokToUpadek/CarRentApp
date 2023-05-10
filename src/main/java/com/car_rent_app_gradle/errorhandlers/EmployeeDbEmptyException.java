package com.car_rent_app_gradle.errorhandlers;

public class EmployeeDbEmptyException extends Exception{
    public EmployeeDbEmptyException() {super(ExceptionMessagesEnum.EMPLOYEE_DB_EMPTY_EXCEPTION.getValue());
    }
}

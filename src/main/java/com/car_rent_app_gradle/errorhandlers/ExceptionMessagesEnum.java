package com.car_rent_app_gradle.errorhandlers;

public enum ExceptionMessagesEnum {
    APP_DB_EXCEPTION("Error while fetching newly created user"),
    EMPTY_AUTH_EXCEPTION("Authentication data missing"),
    VEHICLE_LIST_EMPTY_EXCEPTION("No vehicles available"),

    EMPLOYEE_DB_EMPTY_EXCEPTION("Employee list is empty");

    private final String value;

    ExceptionMessagesEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

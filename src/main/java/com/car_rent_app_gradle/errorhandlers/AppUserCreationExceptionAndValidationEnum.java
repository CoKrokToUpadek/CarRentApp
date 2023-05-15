package com.car_rent_app_gradle.errorhandlers;

public enum AppUserCreationExceptionAndValidationEnum {

    ERR_WRONG_EMAIL_FORMAT ("Email Format Doesnt match regex"),
    ERR_MISSING_INFORMATION ("Information provided in form was incomplete or invalid."),
    ERR_LOGIN_TAKEN("Login was already taken."),
    ERR_EMAIL_TAKEN( "Account with specified email already exist."),
    ERR_WRONG_LOGIN("Login must be at least 4 characters long."),
    ERR_WRONG_PASSWORD("A password must be at least 8 characters. It has to have at least one letter and one digit."),

    EMPLOYEE_ADD_SUCCESS("Employee was created successfully."),
    EMPLOYEE_DB_EMPTY_EXCEPTION("Employee list is empty"),
    VALIDATION_PASS("Data is valid");

    private final String value;

    AppUserCreationExceptionAndValidationEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

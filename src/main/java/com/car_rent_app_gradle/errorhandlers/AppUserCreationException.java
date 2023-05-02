package com.car_rent_app_gradle.errorhandlers;

public class AppUserCreationException extends Exception{

    public static final String ERR_WRONG_EMAIL_FORMAT = "Email Format Doesnt match regex";
    public static final String ERR_MISSING_INFORMATION = "Information provided in form was incomplete or invalid.";
    public static final String ERR_LOGIN_TAKEN = "Login was already taken.";
    public static final String ERR_EMAIL_TAKEN = "Account with specified email already exist.";
    public static final String ERR_WRONG_LOGIN = "Login must be at least 4 characters long.";
    public static final String ERR_WRONG_PASSWORD = "A password must be at least 8 characters. It has to have at least one letter and one digit.";

    public AppUserCreationException(String message) {
        super(message);
    }

}

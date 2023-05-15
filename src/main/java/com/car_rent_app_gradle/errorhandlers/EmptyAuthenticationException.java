package com.car_rent_app_gradle.errorhandlers;

public class EmptyAuthenticationException extends Exception{
    public EmptyAuthenticationException() {
        super(AppOutputMessagesEnum.EMPTY_AUTH_EXCEPTION.getValue());
    }
}

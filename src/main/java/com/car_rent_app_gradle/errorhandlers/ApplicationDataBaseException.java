package com.car_rent_app_gradle.errorhandlers;

import java.util.NoSuchElementException;

public class ApplicationDataBaseException extends NoSuchElementException {
    public ApplicationDataBaseException() {
        super(ExceptionMessagesEnum.APP_DB_EXCEPTION.getValue());
    }
}

package com.car_rent_app_gradle.errorhandlers;

import java.util.NoSuchElementException;

public class ApplicationDataBaseException extends NoSuchElementException {
    public ApplicationDataBaseException() {
        super(AppOutputMessagesEnum.APP_DB_EXCEPTION.getValue());
    }
}

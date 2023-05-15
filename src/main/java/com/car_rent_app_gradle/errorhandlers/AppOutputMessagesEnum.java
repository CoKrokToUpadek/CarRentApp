package com.car_rent_app_gradle.errorhandlers;

public enum AppOutputMessagesEnum {
    APP_DB_EXCEPTION("Error while fetching data. Contact administration if the problem persists"),
    EMPTY_AUTH_EXCEPTION("Authentication data missing");

    private final String value;

    AppOutputMessagesEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

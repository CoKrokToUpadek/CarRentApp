package com.car_rent_app_gradle.errorhandlers;

public enum AccountDataModificationEnum {
    ACCOUNT_NO_CHANGES_EXCEPTION("No field was changed"),
    ACCOUNT_MISSING_INFORMATION_EXCEPTION("Fields are empty or missing. Check if all fields were mapped properly."),
    ACCOUNT_DATA_MODIFICATION_SUCCESS("Personal information data changed successfully.");
    private final String value;

    AccountDataModificationEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

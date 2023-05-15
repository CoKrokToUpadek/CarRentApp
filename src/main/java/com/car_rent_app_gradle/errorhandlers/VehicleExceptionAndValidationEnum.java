package com.car_rent_app_gradle.errorhandlers;

public enum VehicleExceptionAndValidationEnum {

    VEHICLE_DATA_MISSING_EXCEPTION("Fill every fields related to vehicle."),
    VEHICLE_LIST_EMPTY_EXCEPTION("No vehicles available"),
    VEHICLE_ALREADY_IN_DB_EXCEPTION("Number plates are already assigned to another vehicle."),
    VEHICLE_ADD_SUCCESS("vehicle added successfully");

    private final String value;

    VehicleExceptionAndValidationEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

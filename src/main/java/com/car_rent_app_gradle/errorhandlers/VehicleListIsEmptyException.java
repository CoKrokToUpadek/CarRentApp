package com.car_rent_app_gradle.errorhandlers;

public class VehicleListIsEmptyException extends Exception{
    public VehicleListIsEmptyException() {
        super(VehicleExceptionAndValidationEnum.VEHICLE_LIST_EMPTY_EXCEPTION.getValue());
    }
}

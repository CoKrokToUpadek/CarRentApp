package com.car_rent_app_gradle.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleForEmployeesDto {

    @JsonProperty("vehicleId")
    private Long vehicleId;

    @NonNull
    @JsonProperty("vehicleStatus")
    private String vehicleStatus;

    @NonNull
    @JsonProperty("vehicleIsAvailable")
    private Boolean vehicleNoLongerAvailable;

    @NonNull
    @JsonProperty("vehicleBrand")
    private String vehicleBrand;

    @NonNull
    @JsonProperty("vehicleModel")
    private String vehicleModel;

    @NonNull
    @JsonProperty("vehicleType")
    private String vehicleType;

    @NonNull
    @JsonProperty("vehicleCondition")
    private String vehicleCondition;

    @NonNull
    @JsonProperty("vehicleDailyPrice")
    private Double vehicleDailyPrice;

    @NonNull
    @JsonProperty("vehiclePlateNumber")
    private String vehiclePlateNumber;

    @NonNull
    @JsonProperty("vehicleMillage")
    private Integer vehicleMillage;



}

package com.car_rent_app_gradle.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleForCustomersDto {

    @JsonProperty("id")//don't put it on display
    private Long vehicleId;

    @JsonProperty("vehicleStatus")
    private String vehicleStatus;

    @JsonProperty("vehicleBrand")
    private String vehicleBrand;

    @JsonProperty("vehicleModel")
    private String vehicleModel;

    @JsonProperty("vehicleType")
    private String vehicleType;

    @JsonProperty("vehicleDailyPrice")
    private Double vehicleDailyPrice;

}

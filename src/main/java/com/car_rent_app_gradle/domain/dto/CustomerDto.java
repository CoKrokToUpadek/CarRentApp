package com.car_rent_app_gradle.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;



@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDto {

    @JsonProperty("customerId")
    private Long customerId;
    @NonNull
    @JsonProperty("customerFirstName")
    private String customerFirstName;
    @NonNull
    @JsonProperty("customerLastName")
    private String customerLastName;
    @NonNull
    @JsonProperty("customerDrivingLicense")
    private String customerDrivingLicense;
    @NonNull
    @JsonProperty("customerCountry")
    private String customerCountry;
    @NonNull
    @JsonProperty("customerCity")
    private String customerCity;
    @NonNull
    @JsonProperty("customerStreetAndHouseNo")
    private String customerStreetAndHouseNo;
    /*phone No, secondary mail etc*/
    @NonNull
    @JsonProperty("customerContact")
    private String customerContact;
}

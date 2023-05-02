package com.car_rent_app_gradle.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDto {



    @JsonProperty("employeeId")
    private Long employeeId;
    @NonNull
    @JsonProperty("employeeSecurityNumber")
    private String employeeSecurityNumber;
    @NonNull
    @JsonProperty("employeeFirstName")
    private String employeeFirstName;
    @NonNull
    @JsonProperty("employeeLastName")
    private String employeeLastName;
    @NonNull
    @JsonProperty("employeeCountry")
    private String employeeCountry;
    @NonNull
    @JsonProperty("employeeCity")
    private String employeeCity;
    @NonNull
    @JsonProperty("employeeStreetAndHouseNo")
    private String employeeStreetAndHouseNo;
    @NonNull
    @JsonProperty("employeeContact")
    private String employeeContact;
    @NonNull
    @JsonProperty("employeeResponsibilities")
    private String employeeResponsibilities;
    @NonNull
    @JsonProperty("employeeJoinedDate")
    private LocalDate employeeJoinedDate;
    @NonNull
    @JsonProperty("employeeSalary")
    private Double employeeSalary;


}

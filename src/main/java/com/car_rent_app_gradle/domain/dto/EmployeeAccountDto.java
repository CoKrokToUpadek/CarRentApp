package com.car_rent_app_gradle.domain.dto;

import com.car_rent_app_gradle.client.enums.RolesList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.ALWAYS)
public class EmployeeAccountDto extends CommonFieldsAccountDto {
    @NonNull
    @JsonProperty("securityNumber")
    private String securityNumber;

    @NonNull
    @JsonProperty("responsibilities")
    private String responsibilities;
    @NonNull
    @JsonProperty("joinedDate")
    private LocalDate joinedDate;
    @NonNull
    @JsonProperty("employeeSalary")
    private Double employeeSalary;

    @NonNull
    @JsonProperty("systemUserRole")
    private RolesList systemUserRole;
}

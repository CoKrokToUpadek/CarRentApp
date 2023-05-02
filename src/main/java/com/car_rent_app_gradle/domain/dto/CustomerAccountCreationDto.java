package com.car_rent_app_gradle.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


/*made as separate dto for simplicity of not having to send multiple object in account creation request*/
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerAccountCreationDto {

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
    //AppUserDetailsPart
    @JsonProperty("systemUserLogin")
    private String systemUserLogin;
    @NonNull
    @JsonProperty("systemUserPassword")
    private String systemUserPassword;
    @NonNull
    @JsonProperty("systemUserEmail")
    private String systemUserEmail;
}

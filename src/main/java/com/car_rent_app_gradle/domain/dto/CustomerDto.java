package com.car_rent_app_gradle.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
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

    @JsonProperty("appUserDetails")
    private AppUserDetailsDto appUserDetailsDto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerDto that = (CustomerDto) o;

        if (!customerFirstName.equals(that.customerFirstName)) return false;
        if (!customerLastName.equals(that.customerLastName)) return false;
        if (!customerDrivingLicense.equals(that.customerDrivingLicense)) return false;
        if (!customerCountry.equals(that.customerCountry)) return false;
        if (!customerCity.equals(that.customerCity)) return false;
        if (!customerStreetAndHouseNo.equals(that.customerStreetAndHouseNo)) return false;
        return customerContact.equals(that.customerContact);
    }

    @Override
    public int hashCode() {
        int result = customerFirstName.hashCode();
        result = 31 * result + customerLastName.hashCode();
        result = 31 * result + customerDrivingLicense.hashCode();
        result = 31 * result + customerCountry.hashCode();
        result = 31 * result + customerCity.hashCode();
        result = 31 * result + customerStreetAndHouseNo.hashCode();
        result = 31 * result + customerContact.hashCode();
        return result;
    }
}

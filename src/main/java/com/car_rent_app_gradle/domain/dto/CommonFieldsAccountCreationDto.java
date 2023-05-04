package com.car_rent_app_gradle.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
public class CommonFieldsAccountCreationDto {

    @NonNull
    @JsonProperty("firstName")
    private String firstName;
    @NonNull
    @JsonProperty("lastName")
    private String lastName;
    @NonNull
    @JsonProperty("country")
    private String country;
    @NonNull
    @JsonProperty("city")
    private String city;
    @NonNull
    @JsonProperty("streetAndHouseNo")
    private String streetAndHouseNo;
    @NonNull
    @JsonProperty("contact")
    private String contact;

    @NonNull
    @JsonProperty("systemUserLogin")
    private String systemUserLogin;
    @NonNull
    @JsonProperty("systemUserPassword")
    private String systemUserPassword;
    @NonNull
    @JsonProperty("systemUserEmail")
    private String systemUserEmail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommonFieldsAccountCreationDto that = (CommonFieldsAccountCreationDto) o;

        if (!firstName.equals(that.firstName)) return false;
        if (!lastName.equals(that.lastName)) return false;
        if (!country.equals(that.country)) return false;
        if (!city.equals(that.city)) return false;
        if (!streetAndHouseNo.equals(that.streetAndHouseNo)) return false;
        if (!contact.equals(that.contact)) return false;
        if (!Objects.equals(systemUserLogin, that.systemUserLogin))
            return false;
        if (!systemUserPassword.equals(that.systemUserPassword)) return false;
        return systemUserEmail.equals(that.systemUserEmail);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + streetAndHouseNo.hashCode();
        result = 31 * result + contact.hashCode();
        result = 31 * result + (systemUserLogin != null ? systemUserLogin.hashCode() : 0);
        result = 31 * result + systemUserPassword.hashCode();
        result = 31 * result + systemUserEmail.hashCode();
        return result;
    }
}

package com.car_rent_app_gradle.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.ALWAYS)
public class CustomerAccountDto extends CommonFieldsAccountDto {

     @NonNull
     @JsonProperty("drivingLicense")
    private String drivingLicense;

}

package com.car_rent_app_gradle.domain.dto;


import com.car_rent_app_gradle.client.enums.RolesList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppUserDetailsDto {

    @JsonProperty("systemUserId")
    private Long systemUserId;
    @NonNull
    @JsonProperty("systemUserLogin")
    private String systemUserLogin;
    @NonNull
    @JsonProperty("systemUserPassword")
    private String systemUserPassword;
    @NonNull
    @JsonProperty("systemUserEmail")
    private String systemUserEmail;
    @NonNull
    @JsonProperty("systemUserRole")
    private RolesList systemUserRole;
    @NonNull
    @JsonProperty("systemUserAccountNonExpired")
    private Boolean systemUserAccountNonExpired;
    @NonNull
    @JsonProperty("systemUserAccountNonLocked")
    private Boolean systemUserAccountNonLocked;
    @NonNull
    @JsonProperty("systemUserCredentialsNonExpired")
    private Boolean systemUserCredentialsNonExpired;
    @NonNull
    @JsonProperty("systemUserAccountEnabled")
    private Boolean systemUserAccountEnabled;

}

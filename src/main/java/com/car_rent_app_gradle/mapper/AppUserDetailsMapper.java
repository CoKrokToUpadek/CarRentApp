package com.car_rent_app_gradle.mapper;

import com.car_rent_app_gradle.client.enums.RolesList;
import com.car_rent_app_gradle.domain.dto.AppUserDetailsDto;
import com.car_rent_app_gradle.domain.entity.AppUserDetailsEntity;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsMapper {
    //without ID as it will be generated at this stage
    public AppUserDetailsEntity mapToAppUserDetailsEntity(AppUserDetailsDto appUserDetailsDto){
        return new AppUserDetailsEntity(appUserDetailsDto.getSystemUserLogin(), appUserDetailsDto.getSystemUserPassword(),
                appUserDetailsDto.getSystemUserRole().toString(),appUserDetailsDto.getSystemUserAccountNonExpired(),
                appUserDetailsDto.getSystemUserAccountNonLocked(),appUserDetailsDto.getSystemUserCredentialsNonExpired(),
                appUserDetailsDto.getSystemUserAccountEnabled());
    }
    //with ID
    public AppUserDetailsDto mapToAppUserDetailsDto(AppUserDetailsEntity appUserDetailsEntity){
        return new AppUserDetailsDto(appUserDetailsEntity.getSystemUserId(), appUserDetailsEntity.getSystemUserLogin(), appUserDetailsEntity.getSystemUserPassword(),
                RolesList.valueOf(appUserDetailsEntity.getSystemUserRole()),appUserDetailsEntity.getSystemUserAccountNonExpired(),
                appUserDetailsEntity.getSystemUserAccountNonLocked(),appUserDetailsEntity.getSystemUserCredentialsNonExpired(),
                appUserDetailsEntity.getSystemUserAccountEnabled());
    }
}

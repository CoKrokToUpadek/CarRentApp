package com.car_rent_app_gradle.mapper;

import com.car_rent_app_gradle.client.enums.RolesList;
import com.car_rent_app_gradle.domain.dto.AppUserDetailsDto;
import com.car_rent_app_gradle.domain.dto.CustomerAccountDto;
import com.car_rent_app_gradle.domain.dto.EmployeeAccountDto;
import com.car_rent_app_gradle.domain.entity.AppUserDetailsEntity;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsMapper {
    //without ID as it will be generated at this stage
    public AppUserDetailsEntity mapToAppUserDetailsEntity(AppUserDetailsDto appUserDetailsDto){
        return new AppUserDetailsEntity(appUserDetailsDto.getSystemUserLogin(), appUserDetailsDto.getSystemUserPassword(),
                appUserDetailsDto.getSystemUserEmail(), appUserDetailsDto.getSystemUserRole().toString(),
                appUserDetailsDto.getSystemUserAccountNonExpired(), appUserDetailsDto.getSystemUserAccountNonLocked(),
                appUserDetailsDto.getSystemUserCredentialsNonExpired(),
                appUserDetailsDto.getSystemUserAccountEnabled());
    }
    //with ID
    public AppUserDetailsDto mapToAppUserDetailsDto(AppUserDetailsEntity appUserDetailsEntity){
        return new AppUserDetailsDto(appUserDetailsEntity.getSystemUserId(), appUserDetailsEntity.getSystemUserLogin(),
                appUserDetailsEntity.getSystemUserPassword(), appUserDetailsEntity.getSystemUserEmail(),
                RolesList.valueOf(appUserDetailsEntity.getSystemUserRole()),appUserDetailsEntity.getSystemUserAccountNonExpired(),
                appUserDetailsEntity.getSystemUserAccountNonLocked(),appUserDetailsEntity.getSystemUserCredentialsNonExpired(),
                appUserDetailsEntity.getSystemUserAccountEnabled());
    }

    public AppUserDetailsEntity mapToNewCustomerEntity(CustomerAccountDto customerAccountCreationDto){
        return new AppUserDetailsEntity(customerAccountCreationDto.getSystemUserLogin(),
                customerAccountCreationDto.getSystemUserPassword(), customerAccountCreationDto.getSystemUserEmail(),
                RolesList.ROLE_CUSTOMER.toString(),true,true,true,true);
    }

    public AppUserDetailsEntity mapToNewEmployeeEntity(EmployeeAccountDto employeeAccountCreationDto) {
        return new AppUserDetailsEntity(employeeAccountCreationDto.getSystemUserLogin(),
                employeeAccountCreationDto.getSystemUserPassword(), employeeAccountCreationDto.getSystemUserEmail(),
                RolesList.ROLE_EMPLOYEE.toString(),true,true,true,true);
    }
}

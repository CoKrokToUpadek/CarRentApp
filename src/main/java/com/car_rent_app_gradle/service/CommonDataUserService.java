package com.car_rent_app_gradle.service;

import com.car_rent_app_gradle.client.enums.RolesList;
import com.car_rent_app_gradle.domain.dto.CommonFieldsAccountDto;
import com.car_rent_app_gradle.domain.dto.CustomerAccountDto;
import com.car_rent_app_gradle.domain.dto.EmployeeAccountDto;
import com.car_rent_app_gradle.errorhandlers.AppUserCreationExceptionAndValidationEnum;
import com.car_rent_app_gradle.repository.AppUserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

//With a little modifications, this could have been transformed to @Component, but I feel like the code would be "over fragmented"
@Service
@Transactional
public class CommonDataUserService {

    final AppUserDetailsRepository appUserDetailsRepository;

    @Autowired
    public CommonDataUserService(AppUserDetailsRepository appUserDetailsRepository) {
        this.appUserDetailsRepository = appUserDetailsRepository;
    }

    public String validateUserCreationDto(CommonFieldsAccountDto dto, RolesList role){

        final String emailPattern = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

        final  String passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8}.*$";

        final  String loginPattern = "^\\S{4,}$";

        boolean missingInformationForSpecificRole=false;
        boolean missingInformationFromCommonData = Arrays.stream(CommonFieldsAccountDto.class.getDeclaredFields()).anyMatch(field -> {
            field.setAccessible(true);
            try {
                return field.get(dto) == null;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        switch (role) {
            case ROLE_CUSTOMER ->
                    missingInformationForSpecificRole = Arrays.stream(CustomerAccountDto.class.getDeclaredFields()).anyMatch(field -> {
                        field.setAccessible(true);
                        try {
                            return field.get(dto) == null;
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    });
            case ROLE_EMPLOYEE ->
                    missingInformationForSpecificRole = Arrays.stream(EmployeeAccountDto.class.getDeclaredFields()).anyMatch(field -> {
                        field.setAccessible(true);
                        try {
                            return field.get(dto) == null;
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    });
            default -> {
            }
        }

        if (!dto.getSystemUserEmail().matches(emailPattern)){
            return   AppUserCreationExceptionAndValidationEnum.ERR_WRONG_EMAIL_FORMAT.getValue();
        }

        if (!dto.getSystemUserLogin().matches(loginPattern)){
            return AppUserCreationExceptionAndValidationEnum.ERR_WRONG_LOGIN.getValue();
        }

        if (!dto.getSystemUserPassword().matches(passwordPattern)){
            return AppUserCreationExceptionAndValidationEnum.ERR_WRONG_PASSWORD.getValue();
        }

        if (missingInformationFromCommonData || missingInformationForSpecificRole) {
            return AppUserCreationExceptionAndValidationEnum.ERR_MISSING_INFORMATION.getValue();
        }
        if (appUserDetailsRepository.findBySystemUserLogin(dto.getSystemUserLogin()).isPresent()) {
            return AppUserCreationExceptionAndValidationEnum.ERR_LOGIN_TAKEN.getValue();
        }

        if (appUserDetailsRepository.findBySystemUserEmail(dto.getSystemUserEmail()).isPresent()) {
            return AppUserCreationExceptionAndValidationEnum.ERR_EMAIL_TAKEN.getValue();
        }
        return AppUserCreationExceptionAndValidationEnum.VALIDATION_PASS.getValue();
    }
}

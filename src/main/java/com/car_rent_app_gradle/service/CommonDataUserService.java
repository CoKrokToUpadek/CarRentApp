package com.car_rent_app_gradle.service;

import com.car_rent_app_gradle.client.enums.RolesList;
import com.car_rent_app_gradle.domain.dto.CommonFieldsAccountCreationDto;
import com.car_rent_app_gradle.domain.dto.CustomerAccountCreationDto;
import com.car_rent_app_gradle.domain.dto.EmployeeAccountCreationDto;
import com.car_rent_app_gradle.errorhandlers.AppUserCreationValidationAndExceptions;
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

    public String validateUserCreationDto(CommonFieldsAccountCreationDto dto, RolesList role){

        final String emailPattern = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

        final  String passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8}.*$";

        final  String loginPattern = "^\\S{4,}$";

        boolean missingInformationForSpecificRole=false;
        boolean missingInformationFromCommonData = Arrays.stream(CommonFieldsAccountCreationDto.class.getDeclaredFields()).anyMatch(field -> {
            field.setAccessible(true);
            try {
                return field.get(dto) == null;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        switch (role) {
            case ROLE_CUSTOMER ->
                    missingInformationForSpecificRole = Arrays.stream(CustomerAccountCreationDto.class.getDeclaredFields()).anyMatch(field -> {
                        field.setAccessible(true);
                        try {
                            return field.get(dto) == null;
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    });
            case ROLE_EMPLOYEE ->
                    missingInformationForSpecificRole = Arrays.stream(EmployeeAccountCreationDto.class.getDeclaredFields()).anyMatch(field -> {
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
            return   AppUserCreationValidationAndExceptions.ERR_WRONG_EMAIL_FORMAT;
        }

        if (!dto.getSystemUserLogin().matches(loginPattern)){
            return AppUserCreationValidationAndExceptions.ERR_WRONG_LOGIN;
        }

        if (!dto.getSystemUserPassword().matches(passwordPattern)){
            return AppUserCreationValidationAndExceptions.ERR_WRONG_PASSWORD;
        }

        if (missingInformationFromCommonData || missingInformationForSpecificRole) {
            return AppUserCreationValidationAndExceptions.ERR_MISSING_INFORMATION;
        }
        if (appUserDetailsRepository.findBySystemUserLogin(dto.getSystemUserLogin()).isPresent()) {
            return AppUserCreationValidationAndExceptions.ERR_LOGIN_TAKEN;
        }

        if (appUserDetailsRepository.findBySystemUserEmail(dto.getSystemUserEmail()).isPresent()) {
            return AppUserCreationValidationAndExceptions.ERR_EMAIL_TAKEN;
        }
        return AppUserCreationValidationAndExceptions.VALIDATION_PASS;
    }
}

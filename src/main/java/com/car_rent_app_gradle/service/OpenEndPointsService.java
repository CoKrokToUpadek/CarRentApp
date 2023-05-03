package com.car_rent_app_gradle.service;

import com.car_rent_app_gradle.client.security_package.TokenService;
import com.car_rent_app_gradle.domain.dto.CustomerAccountCreationDto;
import com.car_rent_app_gradle.domain.dto.TokenAndRoleDto;
import com.car_rent_app_gradle.domain.dto.VehicleForCustomersDto;

import com.car_rent_app_gradle.domain.entity.AppUserDetailsEntity;
import com.car_rent_app_gradle.domain.entity.CustomerEntity;
import com.car_rent_app_gradle.errorhandlers.ApplicationDataBaseException;
import com.car_rent_app_gradle.errorhandlers.EmptyAuthenticationException;
import com.car_rent_app_gradle.errorhandlers.AppUserCreationException;
import com.car_rent_app_gradle.errorhandlers.VehicleListIsEmptyException;
import com.car_rent_app_gradle.mapper.AppUserDetailsMapper;
import com.car_rent_app_gradle.mapper.CustomerMapper;
import com.car_rent_app_gradle.mapper.VehicleMapper;
import com.car_rent_app_gradle.repository.AppUserDetailsRepository;
import com.car_rent_app_gradle.repository.CustomerRepository;
import com.car_rent_app_gradle.repository.EmployeeRepository;
import com.car_rent_app_gradle.repository.VehicleRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.List;



@Service
@Transactional
public class OpenEndPointsService {
    VehicleRepository vehicleRepository;
    EmployeeRepository employeeRepository;
    CustomerRepository customerRepository;
    AppUserDetailsRepository appUserDetailsRepository;
    VehicleMapper vehicleMapper;
    CustomerMapper customerMapper;
    AppUserDetailsMapper appUserDetailsMapper;
    TokenService tokenService;
    PasswordEncoder encoder;

    private final String emailPattern = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    private final  String passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8}.*$";

    private final  String loginPattern = "^\\S{4,}$";

    public OpenEndPointsService(VehicleRepository vehicleRepository, EmployeeRepository employeeRepository,
                                CustomerRepository customerRepository, AppUserDetailsRepository appUserDetailsRepository,
                                VehicleMapper vehicleMapper, CustomerMapper customerMapper,
                                AppUserDetailsMapper appUserDetailsMapper, TokenService tokenService, PasswordEncoder encoder) {

        this.vehicleRepository = vehicleRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.appUserDetailsRepository = appUserDetailsRepository;
        this.vehicleMapper = vehicleMapper;
        this.customerMapper = customerMapper;
        this.appUserDetailsMapper = appUserDetailsMapper;
        this.tokenService = tokenService;
        this.encoder = encoder;
    }

    public TokenAndRoleDto generateToken(Authentication authentication) throws EmptyAuthenticationException {
        if (authentication == null) {
            throw new EmptyAuthenticationException();
        }
        return tokenService.generateToken(authentication);
    }

    public String createCustomerAccount(CustomerAccountCreationDto customerAccountCreationDto) {
        CustomerEntity customerEntity;
        AppUserDetailsEntity appUserDetailsEntity;
        boolean missingInformation = Arrays.stream(CustomerAccountCreationDto.class.getDeclaredFields()).anyMatch(field -> {
            field.setAccessible(true);
            try {
                return field.get(customerAccountCreationDto) == null;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        if (!customerAccountCreationDto.getSystemUserEmail().matches(emailPattern)){
          return   AppUserCreationException.ERR_WRONG_EMAIL_FORMAT;
        }

        if (!customerAccountCreationDto.getSystemUserLogin().matches(loginPattern)){
            return AppUserCreationException.ERR_WRONG_LOGIN;
        }

        if (!customerAccountCreationDto.getSystemUserPassword().matches(passwordPattern)){
            return AppUserCreationException.ERR_WRONG_PASSWORD;
        }

        if (missingInformation) {
            return AppUserCreationException.ERR_MISSING_INFORMATION;
        }
        if (appUserDetailsRepository.findBySystemUserLogin(customerAccountCreationDto.getSystemUserLogin()).isPresent()) {
            return AppUserCreationException.ERR_LOGIN_TAKEN;
        }

        if (appUserDetailsRepository.findBySystemUserEmail(customerAccountCreationDto.getSystemUserEmail()).isPresent()) {
            return AppUserCreationException.ERR_EMAIL_TAKEN;
        }

        customerAccountCreationDto.setSystemUserPassword(encoder.encode(customerAccountCreationDto.getSystemUserPassword()));
        appUserDetailsEntity = appUserDetailsMapper.mapToNewCustomerEntity(customerAccountCreationDto);
        appUserDetailsRepository.save(appUserDetailsEntity);
   /*      TODO since error below signalize that something went really bad during account creation, it needs some sort
            alarm for administration and tools to remove dead Entity from database */
        appUserDetailsEntity = appUserDetailsRepository.findBySystemUserLoginAndSystemUserEmail(customerAccountCreationDto.getSystemUserLogin()
        ,customerAccountCreationDto.getSystemUserEmail()).orElseThrow(ApplicationDataBaseException::new);
        customerEntity = customerMapper.mapToNewCustomerEntity(customerAccountCreationDto);
        customerEntity.setCarAppUserDetails(appUserDetailsEntity);
        customerRepository.save(customerEntity);
        return "user was created successfully";
    }


    //checks if vehicle is still available for rents
    public List<VehicleForCustomersDto> getVehicleListForClients() throws VehicleListIsEmptyException {
        List<VehicleForCustomersDto> dtoList=vehicleMapper.mapToDtoForCustomerList(vehicleRepository.findAllByVehicleNoLongerAvailable(false));
        if (dtoList.isEmpty()){
            throw new VehicleListIsEmptyException();
        }else {
            return dtoList;
        }


    }

}

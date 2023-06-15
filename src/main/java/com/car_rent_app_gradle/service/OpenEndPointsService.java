package com.car_rent_app_gradle.service;

import com.car_rent_app_gradle.client.enums.RolesList;
import com.car_rent_app_gradle.client.security_package.TokenService;
import com.car_rent_app_gradle.domain.dto.CustomerAccountDto;
import com.car_rent_app_gradle.domain.dto.TokenAndRoleDto;
import com.car_rent_app_gradle.domain.dto.VehicleForCustomersDto;

import com.car_rent_app_gradle.domain.entity.AppUserDetailsEntity;
import com.car_rent_app_gradle.domain.entity.CustomerEntity;
import com.car_rent_app_gradle.errorhandlers.ApplicationDataBaseException;
import com.car_rent_app_gradle.errorhandlers.EmptyAuthenticationException;
import com.car_rent_app_gradle.errorhandlers.AppUserCreationExceptionAndValidationEnum;
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


import java.util.List;



@Service
@Transactional
public class OpenEndPointsService {

    private final  VehicleRepository vehicleRepository;
    private final   EmployeeRepository employeeRepository;
    private final   CustomerRepository customerRepository;
    private final   AppUserDetailsRepository appUserDetailsRepository;
    private final   VehicleMapper vehicleMapper;
    private final   CustomerMapper customerMapper;
    private final    AppUserDetailsMapper appUserDetailsMapper;
    private final    TokenService tokenService;
    private final    PasswordEncoder encoder;
    private final CommonDataUserServiceRecord commonDataUserServiceRecord;

    public OpenEndPointsService(VehicleRepository vehicleRepository, EmployeeRepository employeeRepository,
                                CustomerRepository customerRepository, AppUserDetailsRepository appUserDetailsRepository,
                                VehicleMapper vehicleMapper, CustomerMapper customerMapper, AppUserDetailsMapper appUserDetailsMapper,
                                TokenService tokenService, PasswordEncoder encoder, CommonDataUserServiceRecord commonDataUserServiceRecord) {
        this.vehicleRepository = vehicleRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.appUserDetailsRepository = appUserDetailsRepository;
        this.vehicleMapper = vehicleMapper;
        this.customerMapper = customerMapper;
        this.appUserDetailsMapper = appUserDetailsMapper;
        this.tokenService = tokenService;
        this.encoder = encoder;
        this.commonDataUserServiceRecord = commonDataUserServiceRecord;
    }

    public TokenAndRoleDto generateToken(Authentication authentication) throws EmptyAuthenticationException {
        if (authentication == null) {
            throw new EmptyAuthenticationException();
        }
        return tokenService.generateToken(authentication);
    }

    public String createCustomerAccount(CustomerAccountDto customerAccountCreationDto) {
        CustomerEntity customerEntity;
        AppUserDetailsEntity appUserDetailsEntity;
        String dtoVerification= commonDataUserServiceRecord.validateUserCreationDto(customerAccountCreationDto,RolesList.ROLE_CUSTOMER);
        if(!dtoVerification.equals(AppUserCreationExceptionAndValidationEnum.VALIDATION_PASS.getValue())){
            return dtoVerification;
        }

        customerAccountCreationDto.setSystemUserPassword(encoder.encode(customerAccountCreationDto.getSystemUserPassword()));
        appUserDetailsEntity = appUserDetailsMapper.mapToNewCustomerEntity(customerAccountCreationDto);
        appUserDetailsRepository.save(appUserDetailsEntity);
   /*      TODO since error below signalize that something went really bad during account creation, it needs some sort
            alarm for administration and tools to remove dead Entity from database */
        appUserDetailsEntity = appUserDetailsRepository.findBySystemUserLoginAndSystemUserEmail(customerAccountCreationDto.getSystemUserLogin()
        ,customerAccountCreationDto.getSystemUserEmail()).orElseThrow(ApplicationDataBaseException::new);
        customerEntity = customerMapper.mapToNewCustomerEntity(customerAccountCreationDto);
        customerEntity.setAppUserDetails(appUserDetailsEntity);
        customerRepository.save(customerEntity);
        return "Customer was created successfully.";
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

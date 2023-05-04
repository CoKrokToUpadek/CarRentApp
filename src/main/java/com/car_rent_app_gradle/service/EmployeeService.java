package com.car_rent_app_gradle.service;

import com.car_rent_app_gradle.client.enums.RolesList;
import com.car_rent_app_gradle.domain.dto.EmployeeAccountCreationDto;
import com.car_rent_app_gradle.domain.dto.VehicleForEmployeesDto;
import com.car_rent_app_gradle.domain.entity.AppUserDetailsEntity;
import com.car_rent_app_gradle.domain.entity.EmployeeEntity;
import com.car_rent_app_gradle.domain.entity.VehicleEntity;
import com.car_rent_app_gradle.errorhandlers.AppUserCreationValidationAndExceptions;
import com.car_rent_app_gradle.errorhandlers.ApplicationDataBaseException;
import com.car_rent_app_gradle.mapper.AppUserDetailsMapper;
import com.car_rent_app_gradle.mapper.EmployeeMapper;
import com.car_rent_app_gradle.mapper.VehicleMapper;
import com.car_rent_app_gradle.repository.AppUserDetailsRepository;
import com.car_rent_app_gradle.repository.EmployeeRepository;
import com.car_rent_app_gradle.repository.VehicleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeService {

    VehicleRepository vehicleRepository;
    EmployeeRepository employeeRepository;
    EmployeeMapper employeeMapper;
    VehicleMapper vehicleMapper;
    AppUserDetailsRepository appUserDetailsRepository;

    PasswordEncoder encoder;

    CommonDataUserService commonDataUserService;

    AppUserDetailsMapper appUserDetailsMapper;

    public EmployeeService(VehicleRepository vehicleRepository, EmployeeRepository employeeRepository,
                           EmployeeMapper employeeMapper, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.vehicleMapper = vehicleMapper;
    }

    public String addEmployee(EmployeeAccountCreationDto employeeAccountCreationDto){
        EmployeeEntity employeeEntity;
        AppUserDetailsEntity appUserDetailsEntity;

        String dtoVerification=commonDataUserService.validateUserCreationDto(employeeAccountCreationDto, RolesList.ROLE_CUSTOMER);
        if(!dtoVerification.equals(AppUserCreationValidationAndExceptions.VALIDATION_PASS)){
            return dtoVerification;
        }
        employeeAccountCreationDto.setSystemUserEmail(encoder.encode(employeeAccountCreationDto.getSystemUserPassword()));
        appUserDetailsEntity = appUserDetailsMapper.mapToNewEmployeeEntity(employeeAccountCreationDto);
        appUserDetailsRepository.save(appUserDetailsEntity);
   /*      TODO since error below signalize that something went really bad during account creation, it needs some sort
            alarm for administration and tools to remove dead Entity from database */
        appUserDetailsEntity = appUserDetailsRepository.findBySystemUserLoginAndSystemUserEmail(employeeAccountCreationDto.getSystemUserLogin()
                ,employeeAccountCreationDto.getSystemUserEmail()).orElseThrow(ApplicationDataBaseException::new);
        employeeEntity = employeeMapper.mapToNewEmployeeEntity(employeeAccountCreationDto);
        employeeEntity.setCarAppUserDetails(appUserDetailsEntity);
        employeeRepository.save(employeeEntity);
        return "Employee was created successfully";
    }

    public String addVehicle(VehicleForEmployeesDto vehicle, Long employeeId){
        EmployeeEntity employee=employeeRepository.findByEmployeeId(employeeId);
        if (vehicleRepository.findByVehiclePlateNumber(vehicle.getVehiclePlateNumber()).isPresent()){
            return "car with this plate number is already in the Db";
        }
        else{
            VehicleEntity vehicleEntity=vehicleMapper.mapFromVehicleForEmployeesToEntity(vehicle);
            vehicleEntity.setEmployeeThatRegisteredVehicle(employee);
            vehicleRepository.save(vehicleEntity);
            return "vehicle added successfully";
        }

    }

}

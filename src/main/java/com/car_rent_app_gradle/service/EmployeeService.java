package com.car_rent_app_gradle.service;

import com.car_rent_app_gradle.client.enums.RolesList;
import com.car_rent_app_gradle.client.enums.VehicleStatusList;
import com.car_rent_app_gradle.domain.dto.AppUserDetailsDto;
import com.car_rent_app_gradle.domain.dto.EmployeeAccountCreationDto;
import com.car_rent_app_gradle.domain.dto.EmployeeDto;
import com.car_rent_app_gradle.domain.dto.VehicleForEmployeesDto;
import com.car_rent_app_gradle.domain.entity.AppUserDetailsEntity;
import com.car_rent_app_gradle.domain.entity.EmployeeEntity;
import com.car_rent_app_gradle.domain.entity.VehicleEntity;
import com.car_rent_app_gradle.errorhandlers.*;
import com.car_rent_app_gradle.mapper.AppUserDetailsMapper;
import com.car_rent_app_gradle.mapper.EmployeeMapper;
import com.car_rent_app_gradle.mapper.VehicleMapper;
import com.car_rent_app_gradle.repository.AppUserDetailsRepository;
import com.car_rent_app_gradle.repository.EmployeeRepository;
import com.car_rent_app_gradle.repository.VehicleRepository;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.List;


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
                           EmployeeMapper employeeMapper, VehicleMapper vehicleMapper,
                           AppUserDetailsRepository appUserDetailsRepository, PasswordEncoder encoder,
                           CommonDataUserService commonDataUserService, AppUserDetailsMapper appUserDetailsMapper) {
        this.vehicleRepository = vehicleRepository;
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.vehicleMapper = vehicleMapper;
        this.appUserDetailsRepository = appUserDetailsRepository;
        this.encoder = encoder;
        this.commonDataUserService = commonDataUserService;
        this.appUserDetailsMapper = appUserDetailsMapper;
    }

    public String addEmployee(EmployeeAccountCreationDto employeeAccountCreationDto){
        EmployeeEntity employeeEntity;
        AppUserDetailsEntity appUserDetailsEntity;

        String dtoVerification=commonDataUserService.validateUserCreationDto(employeeAccountCreationDto, RolesList.ROLE_EMPLOYEE);
        if(!dtoVerification.equals(AppUserCreationExceptionAndValidationEnum.VALIDATION_PASS.getValue())){
            return dtoVerification;
        }
        employeeAccountCreationDto.setSystemUserPassword(encoder.encode(employeeAccountCreationDto.getSystemUserPassword()));
        appUserDetailsEntity = appUserDetailsMapper.mapToNewEmployeeEntity(employeeAccountCreationDto);
        appUserDetailsRepository.save(appUserDetailsEntity);
   /*      TODO since error below signalize that something went really bad during account creation, it needs some sort
            alarm for administration and tools to remove dead Entity from database */
        appUserDetailsEntity = appUserDetailsRepository.findBySystemUserLoginAndSystemUserEmail(employeeAccountCreationDto.getSystemUserLogin()
                ,employeeAccountCreationDto.getSystemUserEmail()).orElseThrow(ApplicationDataBaseException::new);
        employeeEntity = employeeMapper.mapToNewEmployeeEntity(employeeAccountCreationDto);
        employeeEntity.setAppUserDetails(appUserDetailsEntity);
        employeeRepository.save(employeeEntity);
        return AppUserCreationExceptionAndValidationEnum.EMPLOYEE_ADD_SUCCESS.getValue();
    }

    public List<EmployeeDto> getEmployeeList() throws EmployeeDbEmptyException {
      List<EmployeeDto> employeeDtoList= employeeRepository.findAll().stream().map(employeeEntity -> {
          AppUserDetailsDto tempDetails = appUserDetailsMapper.mapToAppUserDetailsDto(employeeEntity.getAppUserDetails());
          EmployeeDto tempDto = employeeMapper.mapToEmployeeDto(employeeEntity);
          tempDto.setEmployeeDetails(tempDetails);
          return tempDto;
      }).toList();
      if (employeeDtoList.isEmpty()){
          throw new EmployeeDbEmptyException();
      }

    return employeeDtoList;
    }

    public String addNewVehicle(VehicleForEmployeesDto vehicle, SecurityContext context){
     boolean  missingInformation = Arrays.stream(VehicleForEmployeesDto.class.getDeclaredFields())
             .filter(field -> !field.getName().equals("vehicleId"))
             .filter(field -> !field.getName().equals("vehicleNoLongerAvailable"))
             .filter(field -> !field.getName().equals("vehicleStatus"))
             .anyMatch(field -> {
            field.setAccessible(true);
            try {
                return field.get(vehicle) == null;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
     if (missingInformation){
         return VehicleExceptionAndValidationEnum.VEHICLE_DATA_MISSING_EXCEPTION.getValue();
     }
        AppUserDetailsEntity entity=appUserDetailsRepository.findBySystemUserLogin(context.getAuthentication().getName()).
                orElseThrow(ApplicationDataBaseException::new);
        EmployeeEntity employee=employeeRepository.findByAppUserDetails(entity);
        if (vehicleRepository.findByVehiclePlateNumber(vehicle.getVehiclePlateNumber()).isPresent()){
            return VehicleExceptionAndValidationEnum.VEHICLE_ALREADY_IN_DB_EXCEPTION.getValue();
        }
        else{
            vehicle.setVehicleNoLongerAvailable(false);
            vehicle.setVehicleStatus(VehicleStatusList.ADDED_FOR_THE_FIRST_TIME.toString());
            VehicleEntity vehicleEntity=vehicleMapper.mapFromVehicleForEmployeesToEntity(vehicle);
            vehicleEntity.setEmployeeThatRegisteredVehicle(employee);
            vehicleRepository.save(vehicleEntity);
            return VehicleExceptionAndValidationEnum.VEHICLE_ADD_SUCCESS.getValue();
        }
    }
}

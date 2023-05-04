package com.car_rent_app_gradle.mapper;

import com.car_rent_app_gradle.domain.dto.CustomerAccountCreationDto;
import com.car_rent_app_gradle.domain.dto.EmployeeAccountCreationDto;
import com.car_rent_app_gradle.domain.dto.EmployeeDto;
import com.car_rent_app_gradle.domain.entity.EmployeeEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmployeeMapper {
    ///important to set user details here
    //without ID as it will be generated at this stage
    public EmployeeEntity mapToEmployeeEntity(EmployeeDto employeeDto){
        return new EmployeeEntity(employeeDto.getEmployeeSecurityNumber(), employeeDto.getEmployeeFirstName(), employeeDto.getEmployeeLastName(),
                employeeDto.getEmployeeCountry(), employeeDto.getEmployeeCity(), employeeDto.getEmployeeStreetAndHouseNo(),
                employeeDto.getEmployeeContact(), employeeDto.getEmployeeJoinedDate(), employeeDto.getEmployeeSalary());
    }
    //with ID
    public EmployeeDto mapToEmployeeDto(EmployeeEntity employeeEntity){
        return new EmployeeDto(employeeEntity.getEmployeeId(), employeeEntity.getEmployeeSecurityNumber(), employeeEntity.getEmployeeFirstName(),
                employeeEntity.getEmployeeLastName(), employeeEntity.getEmployeeCountry(), employeeEntity.getEmployeeCity(),
                employeeEntity.getEmployeeStreetAndHouseNo(), employeeEntity.getEmployeeContact(),
                employeeEntity.getEmployeeResponsibilities(),employeeEntity.getEmployeeJoinedDate(), employeeEntity.getEmployeeSalary());
    }


    public EmployeeEntity mapToNewEmployeeEntity(EmployeeAccountCreationDto employeeAccountCreationDto){
        return new EmployeeEntity(employeeAccountCreationDto.getSecurityNumber(), employeeAccountCreationDto.getFirstName(),
                employeeAccountCreationDto.getLastName(), employeeAccountCreationDto.getCountry(), employeeAccountCreationDto.getContact(),
                employeeAccountCreationDto.getStreetAndHouseNo(), employeeAccountCreationDto.getContact(), employeeAccountCreationDto.getJoinedDate(),
                employeeAccountCreationDto.getEmployeeSalary());
    }
}

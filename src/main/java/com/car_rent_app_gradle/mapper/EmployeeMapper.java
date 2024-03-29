package com.car_rent_app_gradle.mapper;

import com.car_rent_app_gradle.domain.dto.EmployeeAccountDto;
import com.car_rent_app_gradle.domain.dto.EmployeeDto;
import com.car_rent_app_gradle.domain.entity.EmployeeEntity;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapper {

    ///important to set user details here
    //without ID as it will be generated at this stage
    public EmployeeEntity mapToEmployeeEntity(EmployeeDto employeeDto){
        return new EmployeeEntity(employeeDto.getEmployeeSecurityNumber(), employeeDto.getEmployeeFirstName(), employeeDto.getEmployeeLastName(),
                employeeDto.getEmployeeCountry(), employeeDto.getEmployeeCity(), employeeDto.getEmployeeStreetAndHouseNo(),
                employeeDto.getEmployeeContact(), employeeDto.getEmployeeResponsibilities(),employeeDto.getEmployeeJoinedDate(), employeeDto.getEmployeeSalary());
    }
    //with ID
    //again, set user details here (I want to keep mappers as separate, and not use in the another)
    public EmployeeDto mapToEmployeeDto(EmployeeEntity employeeEntity){
        return new EmployeeDto(employeeEntity.getEmployeeId(), employeeEntity.getEmployeeSecurityNumber(), employeeEntity.getEmployeeFirstName(),
                employeeEntity.getEmployeeLastName(), employeeEntity.getEmployeeCountry(), employeeEntity.getEmployeeCity(),
                employeeEntity.getEmployeeStreetAndHouseNo(), employeeEntity.getEmployeeContact(),
                employeeEntity.getEmployeeResponsibilities(),employeeEntity.getEmployeeJoinedDate(), employeeEntity.getEmployeeSalary(),null);
    }


    public EmployeeEntity mapToNewEmployeeEntity(EmployeeAccountDto employeeAccountCreationDto){
        return new EmployeeEntity(employeeAccountCreationDto.getSecurityNumber(), employeeAccountCreationDto.getFirstName(),
                employeeAccountCreationDto.getLastName(), employeeAccountCreationDto.getCountry(), employeeAccountCreationDto.getContact(),
                employeeAccountCreationDto.getStreetAndHouseNo(), employeeAccountCreationDto.getContact(), employeeAccountCreationDto.getResponsibilities(),
                employeeAccountCreationDto.getJoinedDate(),
                employeeAccountCreationDto.getEmployeeSalary());
    }
}

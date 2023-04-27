package com.car_rent_app_gradle.service;

import com.car_rent_app_gradle.domain.dto.EmployeeDto;
import com.car_rent_app_gradle.mapper.EmployeeMapper;
import com.car_rent_app_gradle.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeService {

    EmployeeRepository employeeRepository;

    EmployeeMapper employeeMapper;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public String addEmployee(EmployeeDto employeeDto){
     return "";
    }

}

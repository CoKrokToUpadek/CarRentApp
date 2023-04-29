package com.car_rent_app_gradle.service;

import com.car_rent_app_gradle.domain.dto.EmployeeDto;
import com.car_rent_app_gradle.domain.dto.VehicleForEmployeesDto;
import com.car_rent_app_gradle.domain.entity.EmployeeEntity;
import com.car_rent_app_gradle.domain.entity.VehicleEntity;
import com.car_rent_app_gradle.mapper.EmployeeMapper;
import com.car_rent_app_gradle.mapper.VehicleMapper;
import com.car_rent_app_gradle.repository.EmployeeRepository;
import com.car_rent_app_gradle.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeService {

    VehicleRepository vehicleRepository;
    EmployeeRepository employeeRepository;
    EmployeeMapper employeeMapper;
    VehicleMapper vehicleMapper;

    public EmployeeService(VehicleRepository vehicleRepository, EmployeeRepository employeeRepository,
                           EmployeeMapper employeeMapper, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.vehicleMapper = vehicleMapper;
    }

    public String addEmployee(EmployeeDto employeeDto){


        return "";
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

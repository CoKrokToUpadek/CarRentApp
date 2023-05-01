package com.car_rent_app_gradle.service;

import com.car_rent_app_gradle.client.security_package.TokenService;
import com.car_rent_app_gradle.domain.dto.TokenAndRoleDto;
import com.car_rent_app_gradle.domain.dto.VehicleForCustomersDto;

import com.car_rent_app_gradle.mapper.VehicleMapper;
import com.car_rent_app_gradle.repository.EmployeeRepository;
import com.car_rent_app_gradle.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OpenEndPointsService {
    VehicleRepository vehicleRepository;
    EmployeeRepository employeeRepository;
    VehicleMapper vehicleMapper;
    TokenService tokenService;
    @Autowired
    public OpenEndPointsService(VehicleRepository vehicleRepository, EmployeeRepository employeeRepository, VehicleMapper vehicleMapper, TokenService tokenService) {
        this.vehicleRepository = vehicleRepository;
        this.employeeRepository = employeeRepository;
        this.vehicleMapper = vehicleMapper;
        this.tokenService = tokenService;
    }

    public TokenAndRoleDto generateToken(Authentication authentication){
        return tokenService.generateToken(authentication);
    }

    //checks if vehicle is still available for rents
    public  List<VehicleForCustomersDto> getVehicleListForClients(){
        return vehicleMapper.mapToDtoForCustomerList(vehicleRepository.findAllByVehicleNoLongerAvailable(false));
    }

}

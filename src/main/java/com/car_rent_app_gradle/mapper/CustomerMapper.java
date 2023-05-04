package com.car_rent_app_gradle.mapper;

import com.car_rent_app_gradle.domain.dto.CustomerAccountCreationDto;
import com.car_rent_app_gradle.domain.entity.AppUserDetailsEntity;
import com.car_rent_app_gradle.domain.entity.CustomerEntity;
import com.car_rent_app_gradle.domain.entity.EmployeeEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    public CustomerEntity mapToNewCustomerEntity(CustomerAccountCreationDto customerAccountCreationDto) {
//        return new CustomerEntity(customerAccountCreationDto.getCustomerFirstName(), customerAccountCreationDto.getCustomerLastName(),
//                customerAccountCreationDto.getCustomerDrivingLicense(), customerAccountCreationDto.getCustomerCountry(), customerAccountCreationDto.getCustomerCity(),
//                customerAccountCreationDto.getCustomerStreetAndHouseNo(), customerAccountCreationDto.getCustomerContact());
                return new CustomerEntity(customerAccountCreationDto.getFirstName(), customerAccountCreationDto.getLastName(),
                customerAccountCreationDto.getDrivingLicense(), customerAccountCreationDto.getCountry(), customerAccountCreationDto.getCity(),
                customerAccountCreationDto.getStreetAndHouseNo(), customerAccountCreationDto.getContact());
    }
}

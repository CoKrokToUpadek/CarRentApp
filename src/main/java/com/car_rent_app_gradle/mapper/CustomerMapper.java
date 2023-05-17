package com.car_rent_app_gradle.mapper;

import com.car_rent_app_gradle.domain.dto.AppUserDetailsDto;
import com.car_rent_app_gradle.domain.dto.CustomerAccountDto;
import com.car_rent_app_gradle.domain.dto.CustomerDto;
import com.car_rent_app_gradle.domain.entity.AppUserDetailsEntity;
import com.car_rent_app_gradle.domain.entity.CustomerEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    public CustomerEntity mapToNewCustomerEntity(CustomerAccountDto customerAccountCreationDto) {
//        return new CustomerEntity(customerAccountCreationDto.getCustomerFirstName(), customerAccountCreationDto.getCustomerLastName(),
//                customerAccountCreationDto.getCustomerDrivingLicense(), customerAccountCreationDto.getCustomerCountry(), customerAccountCreationDto.getCustomerCity(),
//                customerAccountCreationDto.getCustomerStreetAndHouseNo(), customerAccountCreationDto.getCustomerContact());
                return new CustomerEntity(customerAccountCreationDto.getFirstName(), customerAccountCreationDto.getLastName(),
                customerAccountCreationDto.getDrivingLicense(), customerAccountCreationDto.getCountry(), customerAccountCreationDto.getCity(),
                customerAccountCreationDto.getStreetAndHouseNo(), customerAccountCreationDto.getContact());
    }

    public CustomerDto mapToCustomerDto(CustomerEntity customerEntity){
        return new CustomerDto(customerEntity.getCustomerId(), customerEntity.getCustomerFirstName(), customerEntity.getCustomerLastName(),
                customerEntity.getCustomerDrivingLicense(), customerEntity.getCustomerCountry(), customerEntity.getCustomerCity(),
                customerEntity.getCustomerStreetAndHouseNo(), customerEntity.getCustomerContact(),new AppUserDetailsDto()/*needs to be set manually*/);
    }


}

package com.car_rent_app_gradle.service;


import com.car_rent_app_gradle.domain.dto.CustomerDto;
import com.car_rent_app_gradle.domain.entity.AppUserDetailsEntity;
import com.car_rent_app_gradle.domain.entity.CustomerEntity;
import com.car_rent_app_gradle.errorhandlers.AccountDataModificationEnum;
import com.car_rent_app_gradle.errorhandlers.ApplicationDataBaseException;
import com.car_rent_app_gradle.mapper.CustomerMapper;
import com.car_rent_app_gradle.repository.AppUserDetailsRepository;
import com.car_rent_app_gradle.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AppUserDetailsRepository appUserDetailsRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper,
                           AppUserDetailsRepository appUserDetailsRepository) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.appUserDetailsRepository = appUserDetailsRepository;

    }

    public CustomerEntity save(CustomerEntity customerEntity) {
        return customerRepository.save(customerEntity);
    }

//    private CustomerDto fetchPersonalInformation(SecurityContext context){
//        AppUserDetailsEntity appUserDetailsEntity=appUserDetailsRepository.findBySystemUserLogin(context.getAuthentication().getName()).
//                orElseThrow(ApplicationDataBaseException::new);
//        CustomerEntity customerEntity=customerRepository.findByAppUserDetails(appUserDetailsEntity).orElseThrow(ApplicationDataBaseException::new);
//        AppUserDetailsDto appUserDetailsDto=appUserDetailsMapper.mapToAppUserDetailsDto(appUserDetailsEntity);
//        CustomerDto customerDto=customerMapper.mapToCustomerDto(customerEntity);
//        customerDto.setAppUserDetailsDto(appUserDetailsDto);
//        return customerDto;
//    }

    //this data can be modified without any additional verification
    public String changePersonalInformation(String login,CustomerDto customerDto){
        boolean  missingInformation = Arrays.stream(CustomerDto.class.getDeclaredFields())
                .filter(field -> !field.getName().equals("customerId"))
                .filter(field -> !field.getName().equals("appUserDetailsDto"))
                .anyMatch(field -> {
                    field.setAccessible(true);
                    try {
                        return field.get(customerDto) == null;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
        if (missingInformation){
            return AccountDataModificationEnum.ACCOUNT_MISSING_INFORMATION_EXCEPTION.getValue();
        }


       CustomerEntity fetchedCustomer= fetchCustomerEntity(login);
       CustomerDto customerDtoForComparison=customerMapper.mapToCustomerDto(fetchedCustomer);
        if (customerDtoForComparison.equals(customerDto)){
            return AccountDataModificationEnum.ACCOUNT_NO_CHANGES_EXCEPTION.getValue();
        }else {
            fetchedCustomer.setCustomerFirstName(customerDto.getCustomerFirstName());
            fetchedCustomer.setCustomerLastName(customerDto.getCustomerLastName());
            fetchedCustomer.setCustomerDrivingLicense(customerDto.getCustomerDrivingLicense());
            fetchedCustomer.setCustomerCountry(customerDto.getCustomerCountry());
            fetchedCustomer.setCustomerCity(customerDto.getCustomerCity());
            fetchedCustomer.setCustomerStreetAndHouseNo(customerDto.getCustomerStreetAndHouseNo());
            fetchedCustomer.setCustomerContact(customerDto.getCustomerContact());
            save(fetchedCustomer);
            return AccountDataModificationEnum.ACCOUNT_DATA_MODIFICATION_SUCCESS.getValue();
        }
    }

    CustomerEntity fetchCustomerEntity(String login){
        AppUserDetailsEntity appUserDetailsEntity=appUserDetailsRepository.findBySystemUserLogin(login).
                orElseThrow(ApplicationDataBaseException::new);
        return customerRepository.findByAppUserDetails(appUserDetailsEntity).orElseThrow(ApplicationDataBaseException::new);
    }

}

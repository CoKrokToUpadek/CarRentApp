package com.car_rent_app_gradle.service;

import com.car_rent_app_gradle.client.enums.RolesList;
import com.car_rent_app_gradle.domain.dto.*;
import com.car_rent_app_gradle.errorhandlers.AccountDataModificationEnum;
import com.car_rent_app_gradle.errorhandlers.AppUserCreationExceptionAndValidationEnum;
import com.car_rent_app_gradle.errorhandlers.EmployeeDbEmptyException;
import com.car_rent_app_gradle.errorhandlers.VehicleExceptionAndValidationEnum;
import com.car_rent_app_gradle.repository.AppUserDetailsRepository;
import com.car_rent_app_gradle.repository.CustomerRepository;
import com.car_rent_app_gradle.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@TestPropertySource("classpath:application-H2TestDb.properties")
@Transactional
public class CustomerServiceTestSuite {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    OpenEndPointsService openEndPointsService;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    AppUserDetailsRepository appUserDetailsRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerService customerService;

    @BeforeEach
    public void garbageCollector(){
        customerRepository.deleteAll();
        employeeRepository.deleteAll();
        appUserDetailsRepository.deleteAll();
    }

    @Test
    void changePersonalInformationSuccessTest(){
        //given
        String output;
        CustomerDto dto=new CustomerDto("testName","testLastName","testDrivingLicence",
                "testCountry","testCity"
        ,"testHouseNo","testModifiedContact");
        CustomerAccountDto dto2= CustomerAccountDto.builder()
                .firstName("testName").lastName("testLastName").drivingLicense( "testDrivingLicence")
                .country("testCountry").city("testCity").streetAndHouseNo("testHouseNo").contact("testContact")
                .systemUserLogin("testUserLogin123").systemUserPassword("testUserPassword_123")
                .systemUserEmail("testUser@Email123").build();
       openEndPointsService.createCustomerAccount(dto2);
        //when
        output= customerService.changePersonalInformation("testUserLogin123",dto);
        //then
        Assertions.assertEquals(AccountDataModificationEnum.ACCOUNT_DATA_MODIFICATION_SUCCESS.getValue(),output);

    }

    @Test
    void changePersonalInformationNoChangesTest(){
        //given
        String output;
        CustomerDto dto=new CustomerDto("testName","testLastName","testDrivingLicence",
                "testCountry","testCity"
                ,"testHouseNo","testContact");
        CustomerAccountDto dto2= CustomerAccountDto.builder()
                .firstName("testName").lastName("testLastName").drivingLicense( "testDrivingLicence")
                .country("testCountry").city("testCity").streetAndHouseNo("testHouseNo").contact("testContact")
                .systemUserLogin("testUserLogin123").systemUserPassword("testUserPassword_123")
                .systemUserEmail("testUser@Email123").build();
        openEndPointsService.createCustomerAccount(dto2);
        //when
        output= customerService.changePersonalInformation("testUserLogin123",dto);
        //then
        Assertions.assertEquals(AccountDataModificationEnum.ACCOUNT_NO_CHANGES_EXCEPTION.getValue(),output);

    }

}

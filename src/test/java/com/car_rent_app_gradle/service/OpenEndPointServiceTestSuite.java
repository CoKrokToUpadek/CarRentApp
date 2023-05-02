package com.car_rent_app_gradle.service;

import com.car_rent_app_gradle.domain.dto.CustomerAccountCreationDto;
import com.car_rent_app_gradle.domain.dto.TokenAndRoleDto;
import com.car_rent_app_gradle.errorhandlers.AppUserCreationException;
import com.car_rent_app_gradle.errorhandlers.EmptyAuthenticationException;
import com.car_rent_app_gradle.repository.AppUserDetailsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.TestPropertySource;


import java.util.Arrays;
import java.util.List;

@SpringBootTest
@TestPropertySource("classpath:application-H2TestDb.properties")
public class OpenEndPointServiceTestSuite {

    @Autowired
    OpenEndPointsService openEndPointsService;
    @Autowired
    AppUserDetailsRepository repository;

    @Test
    void generateTokenTest() throws EmptyAuthenticationException {
        //given
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        UsernamePasswordAuthenticationToken loginAndPass=new UsernamePasswordAuthenticationToken("admin", "admin", authorities);
        //when
        TokenAndRoleDto dto= openEndPointsService.generateToken(loginAndPass);
        //then
        Assertions.assertEquals("ADMIN",dto.getRole());
        Assertions.assertFalse(dto.getToken().isEmpty());
    }

    @Test
    void createCustomerAccountSuccessTest(){
        //given
        String output;
        CustomerAccountCreationDto dto=new CustomerAccountCreationDto("testName","testLastName",
                "testDrivingLicence","testCountry","testCity","testHouseNo",
                "testContact","testUserLogin123","testUserPassword_123","testUser@Email123");

        //when
        output= openEndPointsService.createCustomerAccount(dto);
        //then
        Assertions.assertEquals("user was created successfully",output);


//        //when
//        dto.setCustomerCity("");
//        output= openEndPointsService.createCustomerAccount(dto);
//        //then missing information
//        Assertions.assertEquals(AppUserCreationException.ERR_MISSING_INFORMATION,output);
    }


    @Test
    void createCustomerAccountLoginTakenTest() {
        String output;
        CustomerAccountCreationDto dto=new CustomerAccountCreationDto("testName","testLastName",
                "testDrivingLicence","testCountry","testCity","testHouseNo",
                "testContact","testUserLogin","testUserPassword_123","testUser@Email");
        //when
        openEndPointsService.createCustomerAccount(dto);
        output= openEndPointsService.createCustomerAccount(dto);
        //then
        Assertions.assertEquals(AppUserCreationException.ERR_LOGIN_TAKEN,output);
    }

    @Test
    void createCustomerAccountEmailTakenTest() {
        String output;
        CustomerAccountCreationDto dto=new CustomerAccountCreationDto("testName","testLastName",
                "testDrivingLicence","testCountry","testCity","testHouseNo",
                "testContact","testUserLogin","testUserPassword_123","testUser@Email");
        //when
        openEndPointsService.createCustomerAccount(dto);
        dto.setSystemUserLogin("newTestLogin");
        output= openEndPointsService.createCustomerAccount(dto);
        //then
        Assertions.assertEquals(AppUserCreationException.ERR_EMAIL_TAKEN,output);
    }

    @Test
    void createCustomerAccountWrongPasswordFormatTest() {
        String output;
        CustomerAccountCreationDto dto=new CustomerAccountCreationDto("testName","testLastName",
                "testDrivingLicence","testCountry","testCity","testHouseNo",
                "testContact","testUserLogin","testUserPassword_123","testUser@Email");
        dto.setSystemUserPassword("123");
        output= openEndPointsService.createCustomerAccount(dto);
        //then
        Assertions.assertEquals(AppUserCreationException.ERR_WRONG_PASSWORD,output);
    }

    @Test
    void createCustomerAccountWrongLoginFormatTest() {
        String output;
        CustomerAccountCreationDto dto=new CustomerAccountCreationDto("testName","testLastName",
                "testDrivingLicence","testCountry","testCity","testHouseNo",
                "testContact","testUserLogin","testUserPassword_123","testUser@Email");
        dto.setSystemUserLogin("123");
        output= openEndPointsService.createCustomerAccount(dto);
        //then
        Assertions.assertEquals(AppUserCreationException.ERR_WRONG_LOGIN,output);
    }

    @Test
    void createCustomerAccountWrongEmailFormatTest() {
        String output;
        CustomerAccountCreationDto dto=new CustomerAccountCreationDto("testName","testLastName",
                "testDrivingLicence","testCountry","testCity","testHouseNo",
                "testContact","testUserLogin","testUserPassword_123","testUser@Email");
        dto.setSystemUserEmail("123");
        output= openEndPointsService.createCustomerAccount(dto);
        //then
        Assertions.assertEquals(AppUserCreationException.ERR_WRONG_EMAIL_FORMAT,output);
    }
}

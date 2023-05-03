package com.car_rent_app_gradle.service;

import com.car_rent_app_gradle.domain.dto.CustomerAccountCreationDto;
import com.car_rent_app_gradle.domain.dto.TokenAndRoleDto;
import com.car_rent_app_gradle.domain.dto.VehicleForCustomersDto;
import com.car_rent_app_gradle.domain.entity.AppUserDetailsEntity;
import com.car_rent_app_gradle.domain.entity.CustomerEntity;
import com.car_rent_app_gradle.domain.entity.EmployeeEntity;
import com.car_rent_app_gradle.domain.entity.VehicleEntity;
import com.car_rent_app_gradle.errorhandlers.AppUserCreationException;
import com.car_rent_app_gradle.errorhandlers.EmptyAuthenticationException;
import com.car_rent_app_gradle.errorhandlers.VehicleListIsEmptyException;
import com.car_rent_app_gradle.mapper.VehicleMapper;
import com.car_rent_app_gradle.repository.AppUserDetailsRepository;
import com.car_rent_app_gradle.repository.EmployeeRepository;
import com.car_rent_app_gradle.repository.VehicleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.TestPropertySource;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@SpringBootTest
@TestPropertySource("classpath:application-H2TestDb.properties")
public class OpenEndPointServiceTestSuite {

    @Autowired
    OpenEndPointsService openEndPointsService;
    @Autowired
    AppUserDetailsRepository repository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    AppUserDetailsRepository appUserDetailsRepository;
    @Autowired
    VehicleMapper vehicleMapper;


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

    @Test
    void getVehicleEmptyListTest(){
        //given
        //when
        Exception exception=   Assertions.assertThrows(VehicleListIsEmptyException.class,()-> {
            openEndPointsService.getVehicleListForClients();
        });
        String msg=exception.getMessage();
        //then
        Assertions.assertEquals("No vehicles available",msg);

    }

    @Test
    void getVehicleListWithValuesTest() throws VehicleListIsEmptyException {
        //given
        AppUserDetailsEntity details=new AppUserDetailsEntity("testLogin","testPassword","testEmail",
                "testRole",true,true,
                true,true);
        EmployeeEntity employee=new EmployeeEntity("123","testFirstName","testLastName","testCountry","testCity",
                "testHouseNo","testContact",LocalDate.of(2000, Calendar.MARCH,2),100.00);
        employee.setCarAppUserDetails(details);
        CustomerEntity customer=new CustomerEntity("testFirstName","testLastName",
                "TestCode","TestCountry","testCity",
                "TestHouseNo","TestContacts");
        customer.setCarAppUserDetails(details);
        VehicleEntity vehicle=new VehicleEntity("tempStatus",false,"testBrand","testModel","testType","testCondition",
                100.00,"testPlateNumber",100);
        vehicle.setEmployeeThatRegisteredVehicle(employee);
        employeeRepository.save(employee);
        vehicleRepository.save(vehicle);
        vehicle=  vehicleRepository.findByVehiclePlateNumber("testPlateNumber").get();
        //when
        List<VehicleForCustomersDto> vehicleForCustomersDtoList= openEndPointsService.getVehicleListForClients();

        //then
        Assertions.assertEquals(1,vehicleForCustomersDtoList.size());
        Assertions.assertTrue(vehicleForCustomersDtoList.contains(vehicleMapper.mapToDtoForCustomers(vehicle)));

    }

}

package com.car_rent_app_gradle.service;

import com.car_rent_app_gradle.domain.dto.CustomerAccountCreationDto;
import com.car_rent_app_gradle.domain.dto.TokenAndRoleDto;
import com.car_rent_app_gradle.domain.dto.VehicleForCustomersDto;
import com.car_rent_app_gradle.domain.entity.AppUserDetailsEntity;
import com.car_rent_app_gradle.domain.entity.CustomerEntity;
import com.car_rent_app_gradle.domain.entity.EmployeeEntity;
import com.car_rent_app_gradle.domain.entity.VehicleEntity;
import com.car_rent_app_gradle.errorhandlers.AppUserCreationValidationAndExceptions;
import com.car_rent_app_gradle.errorhandlers.EmptyAuthenticationException;
import com.car_rent_app_gradle.errorhandlers.VehicleListIsEmptyException;
import com.car_rent_app_gradle.mapper.VehicleMapper;
import com.car_rent_app_gradle.repository.AppUserDetailsRepository;
import com.car_rent_app_gradle.repository.CustomerRepository;
import com.car_rent_app_gradle.repository.EmployeeRepository;
import com.car_rent_app_gradle.repository.VehicleRepository;
import org.junit.jupiter.api.AfterEach;
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
    EmployeeRepository employeeRepository;
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    AppUserDetailsRepository appUserDetailsRepository;
    @Autowired
    VehicleMapper vehicleMapper;
    @Autowired
    CustomerRepository customerRepository;


    @AfterEach
    public void garbageCollector(){
//        there was some sort of garbage in memory that generated errors when running tests in bulk,
//        (db should not hold context past single tests, or at least I think it shouldn't with my config)
        vehicleRepository.deleteAll();
        customerRepository.deleteAll();
        employeeRepository.deleteAll();
        appUserDetailsRepository.deleteAll();


    }

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
        CustomerAccountCreationDto dto2=CustomerAccountCreationDto.builder()
                .firstName("testName").lastName("testLastName").drivingLicense( "testDrivingLicence")
                .country("testCountry").city("testCity").streetAndHouseNo("testHouseNo").contact("testContact")
                .systemUserLogin("testUserLogin123").systemUserPassword("testUserPassword_123")
                .systemUserEmail("testUser@Email123").build();
        //when
        output= openEndPointsService.createCustomerAccount(dto2);
        //then
        Assertions.assertEquals("Customer was created successfully.",output);
    }


    @Test
    void createCustomerAccountLoginTakenTest() {
        String output;
        CustomerAccountCreationDto dto=CustomerAccountCreationDto.builder()
                .firstName("testName").lastName("testLastName").drivingLicense( "testDrivingLicence")
                .country("testCountry").city("testCity").streetAndHouseNo("testHouseNo").contact("testContact")
                .systemUserLogin("testUserLogin123").systemUserPassword("testUserPassword_123")
                .systemUserEmail("testUser@Email123").build();
        openEndPointsService.createCustomerAccount(dto);
        //when
        output= openEndPointsService.createCustomerAccount(dto);
        //then
        Assertions.assertEquals(AppUserCreationValidationAndExceptions.ERR_LOGIN_TAKEN,output);
    }

    @Test
    void createCustomerAccountEmailTakenTest() {
        String output;
        CustomerAccountCreationDto dto=CustomerAccountCreationDto.builder()
                .firstName("testName").lastName("testLastName").drivingLicense( "testDrivingLicence")
                .country("testCountry").city("testCity").streetAndHouseNo("testHouseNo").contact("testContact")
                .systemUserLogin("testUserLogin123").systemUserPassword("testUserPassword_123")
                .systemUserEmail("testUser@Email123").build();
        //when
        openEndPointsService.createCustomerAccount(dto);
        dto.setSystemUserLogin("newTestLogin");
        output= openEndPointsService.createCustomerAccount(dto);
        //then
        Assertions.assertEquals(AppUserCreationValidationAndExceptions.ERR_EMAIL_TAKEN,output);
    }

    @Test
    void createCustomerAccountWrongPasswordFormatTest() {
        String output;
        CustomerAccountCreationDto dto=CustomerAccountCreationDto.builder()
                .firstName("testName").lastName("testLastName").drivingLicense( "testDrivingLicence")
                .country("testCountry").city("testCity").streetAndHouseNo("testHouseNo").contact("testContact")
                .systemUserLogin("testUserLogin123").systemUserPassword("testUserPassword_123")
                .systemUserEmail("testUser@Email123").build();
        dto.setSystemUserPassword("123");
        output= openEndPointsService.createCustomerAccount(dto);
        //then
        Assertions.assertEquals(AppUserCreationValidationAndExceptions.ERR_WRONG_PASSWORD,output);
    }

    @Test
    void createCustomerAccountWrongLoginFormatTest() {
        String output;
        CustomerAccountCreationDto dto=CustomerAccountCreationDto.builder()
                .firstName("testName").lastName("testLastName").drivingLicense( "testDrivingLicence")
                .country("testCountry").city("testCity").streetAndHouseNo("testHouseNo").contact("testContact")
                .systemUserLogin("testUserLogin123").systemUserPassword("testUserPassword_123")
                .systemUserEmail("testUser@Email123").build();
        dto.setSystemUserLogin("123");
        output= openEndPointsService.createCustomerAccount(dto);
        //then
        Assertions.assertEquals(AppUserCreationValidationAndExceptions.ERR_WRONG_LOGIN,output);
    }

    @Test
    void createCustomerAccountWrongEmailFormatTest() {
        String output;
        CustomerAccountCreationDto dto=CustomerAccountCreationDto.builder()
                .firstName("testName").lastName("testLastName").drivingLicense( "testDrivingLicence")
                .country("testCountry").city("testCity").streetAndHouseNo("testHouseNo").contact("testContact")
                .systemUserLogin("testUserLogin123").systemUserPassword("testUserPassword_123")
                .systemUserEmail("testUser@Email123").build();
        dto.setSystemUserEmail("123");
        output= openEndPointsService.createCustomerAccount(dto);
        //then
        Assertions.assertEquals(AppUserCreationValidationAndExceptions.ERR_WRONG_EMAIL_FORMAT,output);
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
        AppUserDetailsEntity details=new AppUserDetailsEntity("testLogin","testPassword",
                "testEmail", "testRole",true,true,
                true,true);
        EmployeeEntity employee=new EmployeeEntity("123","testFirstName","testLastName",
                "testCountry","testCity", "testHouseNo",
                "testContact","tempResponsibilities",LocalDate.of(2000, Calendar.MARCH,2),100.00);
        employee.setAppUserDetails(details);
        CustomerEntity customer=new CustomerEntity("testFirstName","testLastName",
                "TestCode","TestCountry","testCity",
                "TestHouseNo","TestContacts");
        customer.setAppUserDetails(details);
        VehicleEntity vehicle=new VehicleEntity("tempStatus",false,"testBrand",
                "testModel","testType","testCondition",
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

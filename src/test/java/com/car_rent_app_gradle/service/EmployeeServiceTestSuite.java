package com.car_rent_app_gradle.service;

import com.car_rent_app_gradle.client.enums.RolesList;
import com.car_rent_app_gradle.domain.dto.EmployeeAccountCreationDto;
import com.car_rent_app_gradle.domain.dto.EmployeeDto;
import com.car_rent_app_gradle.errorhandlers.AppUserCreationValidationAndExceptions;
import com.car_rent_app_gradle.errorhandlers.EmployeeDbEmptyException;
import com.car_rent_app_gradle.repository.AppUserDetailsRepository;
import com.car_rent_app_gradle.repository.CustomerRepository;
import com.car_rent_app_gradle.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@TestPropertySource("classpath:application-H2TestDb.properties")
@Transactional
public class EmployeeServiceTestSuite {

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

    @BeforeEach
    public void garbageCollector(){
        customerRepository.deleteAll();
        employeeRepository.deleteAll();
        appUserDetailsRepository.deleteAll();
    }

    @Test
    void addEmployeeSuccessTest(){
        //given
        String output;
        EmployeeAccountCreationDto dto=EmployeeAccountCreationDto.builder().firstName("testName").lastName("testLastName")
                .country("testCountry").city("testCity").streetAndHouseNo("testHouseNo").contact("testContact")
                .securityNumber("testSecurityNumber").responsibilities("testResponsibilities").joinedDate(LocalDate.now())
                .employeeSalary(2000.00).systemUserRole(RolesList.ROLE_EMPLOYEE)
                .systemUserLogin("testUserLogin1234").systemUserPassword("testUserPassword_123").systemUserEmail("testUser@Email1234")
                .build();
        //when
        output= employeeService.addEmployee(dto);
        //then
        Assertions.assertEquals("Employee was created successfully.",output);

    }

    @Test
    void addEmployeeLoginTakenTest() {
        String output;
        EmployeeAccountCreationDto dto=EmployeeAccountCreationDto.builder().firstName("testName").lastName("testLastName")
                .country("testCountry").city("testCity").streetAndHouseNo("testHouseNo").contact("testContact")
                .securityNumber("testSecurityNumber").responsibilities("testResponsibilities").joinedDate(LocalDate.now())
                .employeeSalary(2000.00).systemUserRole(RolesList.ROLE_EMPLOYEE)
                .systemUserLogin("testUserLogin1234").systemUserPassword("testUserPassword_123").systemUserEmail("testUser@Email1234")
                .build();
        employeeService.addEmployee(dto);
        //when
        output= employeeService.addEmployee(dto);
        //then
        Assertions.assertEquals(AppUserCreationValidationAndExceptions.ERR_LOGIN_TAKEN,output);
    }

    @Test
    void addEmployeeEmailTakenTest() {
        String output;
        EmployeeAccountCreationDto dto2=EmployeeAccountCreationDto.builder().firstName("testName").lastName("testLastName")
                .country("testCountry").city("testCity").streetAndHouseNo("testHouseNo").contact("testContact")
                .securityNumber("testSecurityNumber").responsibilities("testResponsibilities").joinedDate(LocalDate.now())
                .employeeSalary(2000.00).systemUserRole(RolesList.ROLE_EMPLOYEE)
                .systemUserLogin("testUserLogin12345").systemUserPassword("testUserPassword_123").systemUserEmail("testUser@Email1234")
                .build();
        employeeService.addEmployee(dto2);
        //when
        dto2.setSystemUserLogin("newTestUserLogin12345");
        output= employeeService.addEmployee(dto2);
        //then
        Assertions.assertEquals(AppUserCreationValidationAndExceptions.ERR_EMAIL_TAKEN,output);
    }

    @Test
    void addEmployeeEmailWrongPasswordFormatTest() {
        String output;
        EmployeeAccountCreationDto dto=EmployeeAccountCreationDto.builder().firstName("testName").lastName("testLastName")
                .country("testCountry").city("testCity").streetAndHouseNo("testHouseNo").contact("testContact")
                .securityNumber("testSecurityNumber").responsibilities("testResponsibilities").joinedDate(LocalDate.now())
                .employeeSalary(2000.00).systemUserRole(RolesList.ROLE_EMPLOYEE)
                .systemUserLogin("testUserLogin12345").systemUserPassword("testUserPassword_123").systemUserEmail("testUser@Email1234")
                .build();
        dto.setSystemUserPassword("123");
        //when
        output= employeeService.addEmployee(dto);
        //then
        Assertions.assertEquals(AppUserCreationValidationAndExceptions.ERR_WRONG_PASSWORD,output);
    }

    @Test
    void addEmployeeEmailWrongLoginFormatTest() {
        String output;
        EmployeeAccountCreationDto dto=EmployeeAccountCreationDto.builder().firstName("testName").lastName("testLastName")
                .country("testCountry").city("testCity").streetAndHouseNo("testHouseNo").contact("testContact")
                .securityNumber("testSecurityNumber").responsibilities("testResponsibilities").joinedDate(LocalDate.now())
                .employeeSalary(2000.00).systemUserRole(RolesList.ROLE_EMPLOYEE)
                .systemUserLogin("testUserLogin12345").systemUserPassword("testUserPassword_123").systemUserEmail("testUser@Email1234")
                .build();
        dto.setSystemUserLogin("123");
        //when
        output= employeeService.addEmployee(dto);
        //then
        Assertions.assertEquals(AppUserCreationValidationAndExceptions.ERR_WRONG_LOGIN,output);
    }

    @Test
    void addEmployeeEmailWrongEmailFormatTest() {
        String output;
        EmployeeAccountCreationDto dto=EmployeeAccountCreationDto.builder().firstName("testName").lastName("testLastName")
                .country("testCountry").city("testCity").streetAndHouseNo("testHouseNo").contact("testContact")
                .securityNumber("testSecurityNumber").responsibilities("testResponsibilities").joinedDate(LocalDate.now())
                .employeeSalary(2000.00).systemUserRole(RolesList.ROLE_EMPLOYEE)
                .systemUserLogin("testUserLogin12345").systemUserPassword("testUserPassword_123").systemUserEmail("testUser@Email1234")
                .build();
        dto.setSystemUserEmail("123");
        //when
        output= employeeService.addEmployee(dto);
        //then
        Assertions.assertEquals(AppUserCreationValidationAndExceptions.ERR_WRONG_EMAIL_FORMAT,output);
    }


    @Test
    void getEmployeeListWithRecordsTest() throws EmployeeDbEmptyException {
        //given
        EmployeeAccountCreationDto dto=EmployeeAccountCreationDto.builder().firstName("testName").lastName("testLastName")
                .country("testCountry").city("testCity").streetAndHouseNo("testHouseNo").contact("testContact")
                .securityNumber("testSecurityNumber").responsibilities("testResponsibilities").joinedDate(LocalDate.now())
                .employeeSalary(2000.00).systemUserRole(RolesList.ROLE_EMPLOYEE)
                .systemUserLogin("testUserLogin").systemUserPassword("testUserPassword_123").systemUserEmail("testUser@Email")
                .build();
        employeeService.addEmployee(dto);
        //when
        List<EmployeeDto> employeeDtoList=employeeService.getEmployeeList();
        //then
        Assertions.assertEquals(1,employeeDtoList.size());
    }




}

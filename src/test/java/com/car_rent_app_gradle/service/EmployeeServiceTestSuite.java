package com.car_rent_app_gradle.service;

import com.car_rent_app_gradle.client.enums.RolesList;
import com.car_rent_app_gradle.domain.dto.EmployeeAccountDto;
import com.car_rent_app_gradle.domain.dto.EmployeeDto;
import com.car_rent_app_gradle.domain.dto.VehicleForEmployeesDto;
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
        EmployeeAccountDto dto= EmployeeAccountDto.builder().firstName("testName").lastName("testLastName")
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
        EmployeeAccountDto dto= EmployeeAccountDto.builder().firstName("testName").lastName("testLastName")
                .country("testCountry").city("testCity").streetAndHouseNo("testHouseNo").contact("testContact")
                .securityNumber("testSecurityNumber").responsibilities("testResponsibilities").joinedDate(LocalDate.now())
                .employeeSalary(2000.00).systemUserRole(RolesList.ROLE_EMPLOYEE)
                .systemUserLogin("testUserLogin1234").systemUserPassword("testUserPassword_123").systemUserEmail("testUser@Email1234")
                .build();
        employeeService.addEmployee(dto);
        //when
        output= employeeService.addEmployee(dto);
        //then
        Assertions.assertEquals(AppUserCreationExceptionAndValidationEnum.ERR_LOGIN_TAKEN.getValue(),output);
    }

    @Test
    void addEmployeeEmailTakenTest() {
        String output;
        EmployeeAccountDto dto2= EmployeeAccountDto.builder().firstName("testName").lastName("testLastName")
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
        Assertions.assertEquals(AppUserCreationExceptionAndValidationEnum.ERR_EMAIL_TAKEN.getValue(),output);
    }

    @Test
    void addEmployeeEmailWrongPasswordFormatTest() {
        String output;
        EmployeeAccountDto dto= EmployeeAccountDto.builder().firstName("testName").lastName("testLastName")
                .country("testCountry").city("testCity").streetAndHouseNo("testHouseNo").contact("testContact")
                .securityNumber("testSecurityNumber").responsibilities("testResponsibilities").joinedDate(LocalDate.now())
                .employeeSalary(2000.00).systemUserRole(RolesList.ROLE_EMPLOYEE)
                .systemUserLogin("testUserLogin12345").systemUserPassword("testUserPassword_123").systemUserEmail("testUser@Email1234")
                .build();
        dto.setSystemUserPassword("123");
        //when
        output= employeeService.addEmployee(dto);
        //then
        Assertions.assertEquals(AppUserCreationExceptionAndValidationEnum.ERR_WRONG_PASSWORD.getValue(),output);
    }

    @Test
    void addEmployeeEmailWrongLoginFormatTest() {
        String output;
        EmployeeAccountDto dto= EmployeeAccountDto.builder().firstName("testName").lastName("testLastName")
                .country("testCountry").city("testCity").streetAndHouseNo("testHouseNo").contact("testContact")
                .securityNumber("testSecurityNumber").responsibilities("testResponsibilities").joinedDate(LocalDate.now())
                .employeeSalary(2000.00).systemUserRole(RolesList.ROLE_EMPLOYEE)
                .systemUserLogin("testUserLogin12345").systemUserPassword("testUserPassword_123").systemUserEmail("testUser@Email1234")
                .build();
        dto.setSystemUserLogin("123");
        //when
        output= employeeService.addEmployee(dto);
        //then
        Assertions.assertEquals(AppUserCreationExceptionAndValidationEnum.ERR_WRONG_LOGIN.getValue(),output);
    }

    @Test
    void addEmployeeEmailWrongEmailFormatTest() {
        String output;
        EmployeeAccountDto dto= EmployeeAccountDto.builder().firstName("testName").lastName("testLastName")
                .country("testCountry").city("testCity").streetAndHouseNo("testHouseNo").contact("testContact")
                .securityNumber("testSecurityNumber").responsibilities("testResponsibilities").joinedDate(LocalDate.now())
                .employeeSalary(2000.00).systemUserRole(RolesList.ROLE_EMPLOYEE)
                .systemUserLogin("testUserLogin12345").systemUserPassword("testUserPassword_123").systemUserEmail("testUser@Email1234")
                .build();
        dto.setSystemUserEmail("123");
        //when
        output= employeeService.addEmployee(dto);
        //then
        Assertions.assertEquals(AppUserCreationExceptionAndValidationEnum.ERR_WRONG_EMAIL_FORMAT.getValue(),output);
    }


    @Test
    void getEmployeeListWithRecordsTest() throws EmployeeDbEmptyException {
        //given
        EmployeeAccountDto dto= EmployeeAccountDto.builder().firstName("testName").lastName("testLastName")
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




    @Test
    void addNewVehicleSuccessTest(){
        //given
        Authentication authentication = new UsernamePasswordAuthenticationToken("testUserLogin", "testUserPassword_123");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        EmployeeAccountDto dto= EmployeeAccountDto.builder().firstName("testName").lastName("testLastName")
                .country("testCountry").city("testCity").streetAndHouseNo("testHouseNo").contact("testContact")
                .securityNumber("testSecurityNumber").responsibilities("testResponsibilities").joinedDate(LocalDate.now())
                .employeeSalary(2000.00).systemUserRole(RolesList.ROLE_EMPLOYEE)
                .systemUserLogin("testUserLogin").systemUserPassword("testUserPassword_123").systemUserEmail("testUser@Email")
                .build();
        employeeService.addEmployee(dto);
        VehicleForEmployeesDto vehicleDto=new VehicleForEmployeesDto("testBrand",
                "testModel","testType","testCondition",20.00,"testPlateNumber",2000);
        //when
         String msg= employeeService.addNewVehicle(vehicleDto,securityContext);

        //then
        Assertions.assertEquals(VehicleExceptionAndValidationEnum.VEHICLE_ADD_SUCCESS.getValue(),msg);
    }

    @Test
    void addNewVehicleAlreadyInDBTest(){
        //given
        Authentication authentication = new UsernamePasswordAuthenticationToken("testUserLogin", "testUserPassword_123");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        EmployeeAccountDto dto= EmployeeAccountDto.builder().firstName("testName").lastName("testLastName")
                .country("testCountry").city("testCity").streetAndHouseNo("testHouseNo").contact("testContact")
                .securityNumber("testSecurityNumber").responsibilities("testResponsibilities").joinedDate(LocalDate.now())
                .employeeSalary(2000.00).systemUserRole(RolesList.ROLE_EMPLOYEE)
                .systemUserLogin("testUserLogin").systemUserPassword("testUserPassword_123").systemUserEmail("testUser@Email")
                .build();
        employeeService.addEmployee(dto);
        VehicleForEmployeesDto vehicleDto=new VehicleForEmployeesDto("testBrand",
                "testModel","testType","testCondition",20.00,"testPlateNumber",2000);
        employeeService.addNewVehicle(vehicleDto,securityContext);
        //when
        String msg= employeeService.addNewVehicle(vehicleDto,securityContext);
        //then
        Assertions.assertEquals(VehicleExceptionAndValidationEnum.VEHICLE_ALREADY_IN_DB_EXCEPTION.getValue(),msg);
    }


    @Test
    void deleteVehicleSuccessTest(){//TODO requires implementation of additional methods
        //given
        Authentication authentication = new UsernamePasswordAuthenticationToken("testUserLogin", "testUserPassword_123");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        VehicleForEmployeesDto vehicleDto=new VehicleForEmployeesDto("testBrand",
                "testModel","testType","testCondition",20.00,"testPlateNumber",2000);
        EmployeeAccountDto dto= EmployeeAccountDto.builder().firstName("testName").lastName("testLastName")
                .country("testCountry").city("testCity").streetAndHouseNo("testHouseNo").contact("testContact")
                .securityNumber("testSecurityNumber").responsibilities("testResponsibilities").joinedDate(LocalDate.now())
                .employeeSalary(2000.00).systemUserRole(RolesList.ROLE_EMPLOYEE)
                .systemUserLogin("testUserLogin").systemUserPassword("testUserPassword_123").systemUserEmail("testUser@Email")
                .build();
        employeeService.addEmployee(dto);
        employeeService.addNewVehicle(vehicleDto,securityContext);

        //when

    }

}

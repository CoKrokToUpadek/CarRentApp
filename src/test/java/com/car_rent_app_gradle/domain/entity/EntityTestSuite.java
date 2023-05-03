package com.car_rent_app_gradle.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Calendar;


//basic objects tests
@SpringBootTest
@TestPropertySource("classpath:application-H2TestDb.properties")
public class EntityTestSuite {

    @Test
    public void customerCreationWithDetailsEntityTest(){
        //given
       AppUserDetailsEntity details=new AppUserDetailsEntity("testLogin","testPassword","testEmail",
               "testRole",true,true,
               true,true);
       //when
       CustomerEntity customer=new CustomerEntity("testFirstName","testLastName",
               "TestCode","TestCountry","testCity",
               "TestHouseNo","TestContacts");
       customer.setCarAppUserDetails(details);
       //then
        Assertions.assertEquals(customer.getCarAppUserDetails(),details);
    }

    @Test
    public void employeeCreationWithDetailsEntityTest(){
        //given
        AppUserDetailsEntity details=new AppUserDetailsEntity("testLogin","testPassword","testEmail",
                "testRole",true,true,
                true,true);
        //when
        EmployeeEntity employee=new EmployeeEntity("123","testFirstName","testLastName","testCountry","testCity",
                "testHouseNo","testContact", LocalDate.of(2000, Calendar.MARCH,2),100.00);
        employee.setCarAppUserDetails(details);
        //then
        Assertions.assertEquals(employee.getCarAppUserDetails(),details);
    }

    @Test
    public void employeeRegisteringVehicleEntityTest(){
        //given
        AppUserDetailsEntity details=new AppUserDetailsEntity("testLogin","testPassword","testEmail",
                "testRole",true,true,
                true,true);
        EmployeeEntity employee=new EmployeeEntity("123","testFirstName","testLastName","testCountry","testCity",
                "testHouseNo","testContact",LocalDate.of(2000, Calendar.MARCH,2),100.00);
        employee.setCarAppUserDetails(details);
        //when
        VehicleEntity vehicle=new VehicleEntity("tempStatus",true,"testBrand","testModel","testType","testCondition",
                100.00,"testPlateNumber",100);
        vehicle.setEmployeeThatRegisteredVehicle(employee);
        //then
        Assertions.assertEquals(vehicle.getEmployeeThatRegisteredVehicle(),employee);
    }

    @Test
    public void customerMakesReservationEntityTest(){
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
        VehicleEntity vehicle=new VehicleEntity("tempStatus",true,"testBrand","testModel","testType","testCondition",
                100.00,"testPlateNumber",100);
        vehicle.setEmployeeThatRegisteredVehicle(employee);
        //when
        ReservationEntity reservation=new ReservationEntity(LocalDate.of(2000,2,2),"testPickupLocation",
                5,LocalDate.of(2000,2,2),LocalDate.of(2000,2,7),customer,employee,new RentEntity(),vehicle);
        RentEntity rentEntity=new RentEntity(100.00,"testMethod",200.00,
                LocalDate.of(2000,2,2),0.0,reservation.getCustomerThatMadeReservation()
                ,reservation,vehicle);
        reservation.setRentContainedInReservation(rentEntity);

        //then
        Assertions.assertEquals(customer,reservation.getCustomerThatMadeReservation());
        Assertions.assertEquals(employee,reservation.getEmployeeThatManagesReservation());
        Assertions.assertEquals(rentEntity,reservation.getRentContainedInReservation());
        Assertions.assertEquals(vehicle,reservation.getVehicleChosenInReservation());


    }

}

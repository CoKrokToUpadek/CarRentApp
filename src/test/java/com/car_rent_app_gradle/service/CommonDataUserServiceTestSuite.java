package com.car_rent_app_gradle.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application-H2TestDb.properties")
public class CommonDataUserServiceTestSuite {

    @Autowired
    CommonDataUserServiceRecord commonDataUserServiceRecord;

    @Test
    void validateUserCreationDtoEmployeeTest(){
        //given

        //when
        //then
    }

}

package com.car_rent_app_gradle.controller;

import com.car_rent_app_gradle.client.enums.RolesList;
import com.car_rent_app_gradle.client.security_package.*;
import com.car_rent_app_gradle.domain.entity.AppUserDetailsEntity;
import com.car_rent_app_gradle.mapper.AppUserDetailsMapper;
import com.car_rent_app_gradle.mapper.CustomerMapper;
import com.car_rent_app_gradle.mapper.EmployeeMapper;
import com.car_rent_app_gradle.mapper.VehicleMapper;
import com.car_rent_app_gradle.repository.AppUserDetailsRepository;
import com.car_rent_app_gradle.repository.CustomerRepository;
import com.car_rent_app_gradle.repository.EmployeeRepository;
import com.car_rent_app_gradle.repository.VehicleRepository;
import com.car_rent_app_gradle.service.CommonDataUserService;
import com.car_rent_app_gradle.service.EmployeeService;
import com.car_rent_app_gradle.service.OpenEndPointsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;


@WebMvcTest(EmployeeController.class)
@TestPropertySource("classpath:application-H2TestDb.properties")
@Import({OpenEndPointsService.class, TokenService.class,JWTConfig.class
        , AppUserSpringSecurityDetailsService.class, AppUserDetailsService.class, EmployeeService.class,EmployeeMapper.class
        , AppUserDetailsMapper.class, CommonDataUserService.class})
public class EmployeeControllerTestSuite {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EmployeeRepository employeeRepository;
    @MockBean
    AppUserSpringSecurityDetailsService service;
    @MockBean
    AppUserDetailsRepository appUserDetailsRepository;

    @MockBean
    VehicleRepository vehicleRepository;

    @MockBean
    VehicleMapper vehicleMapper;

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    CustomerMapper customerMapper;




    @TestConfiguration
    static class TestConfig {
        /*   I've disabled password encoding for tests to simplify adding temp user (I could probably generate
           BCrypt string add parse it to password field, but I think this is simpler and more readable*/
        @Bean
        public PasswordEncoder passwordEncoder() {
            return NoOpPasswordEncoder.getInstance();
        }
    }

    @BeforeEach
//ok
    void mockUserForTests(){
        AppUserDetailsEntity entity=  new AppUserDetailsEntity("admin","admin","adminMail",
                RolesList.ROLE_ADMIN.toString(),true,true,true,true);
        when(service.loadUserByUsername("admin")).thenReturn(new AppUserDetails(entity));
        when(appUserDetailsRepository.findBySystemUserLoginAndSystemUserEmail(any(),any())).thenReturn(Optional.of(entity));
    }

    @Test
    void addEmployeeMissingInformationTest() throws Exception {
        File jsonFile = new File("src/test/resources/testEmployeeInvalid.json");
        byte[] jsonBytes = Files.readAllBytes(jsonFile.toPath());
        MvcResult result=  mockMvc.perform(MockMvcRequestBuilders.post("/employee/addEmployee").with(httpBasic("admin","admin"))
                        .contentType(MediaType.APPLICATION_JSON).content(jsonBytes))
                    .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Assertions.assertEquals("Information provided in form was incomplete or invalid.", result.getResponse().getContentAsString());
    }

    @Test
    void AddEmployeeValidTest() throws Exception {
        File jsonFile = new File("src/test/resources/testEmployeeValid.json");
        byte[] jsonBytes = Files.readAllBytes(jsonFile.toPath());
        MvcResult result=  mockMvc.perform(MockMvcRequestBuilders.post("/employee/addEmployee").with(httpBasic("admin","admin"))
                        .contentType(MediaType.APPLICATION_JSON).content(jsonBytes))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Assertions.assertEquals("Employee was created successfully.", result.getResponse().getContentAsString());
    }
}

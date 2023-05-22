package com.car_rent_app_gradle.controller;

import com.car_rent_app_gradle.client.enums.RolesList;
import com.car_rent_app_gradle.client.security_package.*;
import com.car_rent_app_gradle.domain.entity.AppUserDetailsEntity;
import com.car_rent_app_gradle.domain.entity.CustomerEntity;
import com.car_rent_app_gradle.domain.entity.EmployeeEntity;
import com.car_rent_app_gradle.errorhandlers.AppUserCreationExceptionAndValidationEnum;
import com.car_rent_app_gradle.errorhandlers.VehicleExceptionAndValidationEnum;
import com.car_rent_app_gradle.mapper.AppUserDetailsMapper;
import com.car_rent_app_gradle.mapper.CustomerMapper;
import com.car_rent_app_gradle.mapper.EmployeeMapper;
import com.car_rent_app_gradle.mapper.VehicleMapper;
import com.car_rent_app_gradle.repository.AppUserDetailsRepository;
import com.car_rent_app_gradle.repository.CustomerRepository;
import com.car_rent_app_gradle.repository.EmployeeRepository;
import com.car_rent_app_gradle.repository.VehicleRepository;
import com.car_rent_app_gradle.service.CommonDataUserService;
import com.car_rent_app_gradle.service.CustomerService;
import com.car_rent_app_gradle.service.EmployeeService;
import com.car_rent_app_gradle.service.OpenEndPointsService;
import org.hamcrest.Matchers;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;


@WebMvcTest(CustomerController.class)
@TestPropertySource("classpath:application-H2TestDb.properties")
@Import({OpenEndPointsService.class, TokenService.class,JWTConfig.class
        , AppUserSpringSecurityDetailsService.class, AppUserDetailsService.class, CustomerService.class,EmployeeMapper.class
        , AppUserDetailsMapper.class, CommonDataUserService.class,CustomerMapper.class,VehicleMapper.class,CustomerMapper.class})
public class CustomerControllerTestSuite {

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
    CustomerRepository customerRepository;



    @TestConfiguration
    static class TestConfig {
        /*   I've disabled password encoding for tests to simplify adding temp user (I could probably generate
           BCrypt string add parse it to password field, but I think this is simpler and more readable*/
        @Bean
        public PasswordEncoder passwordEncoder() {
            return NoOpPasswordEncoder.getInstance();
        }
    }

    @BeforeEach//ok
    void mockUserForTests(){
        AppUserDetailsEntity entity=  new AppUserDetailsEntity("admin","admin","adminMail",
                RolesList.ROLE_CUSTOMER.toString(),true,true,true,true);
        when(service.loadUserByUsername("admin")).thenReturn(new AppUserDetails(entity));
     //   when(appUserDetailsRepository.findBySystemUserLoginAndSystemUserEmail(any(),any())).thenReturn(Optional.of(entity));
    }

    @Test
    void changePersonalInformationValidTest() throws Exception {
        //given
        AppUserDetailsEntity entity=  new AppUserDetailsEntity("admin","admin","adminMail",
                RolesList.ROLE_CUSTOMER.toString(),true,true,true,true);
        CustomerEntity customerEntity=new CustomerEntity(1L,"test_to_be_changed","test",
                "test","test","test","test",
                "test",new ArrayList<>(),new ArrayList<>(),entity);
        File jsonFile = new File("src/test/resources/testCustomerChangedInfoValid.json");
        byte[] jsonBytes = Files.readAllBytes(jsonFile.toPath());
        when(appUserDetailsRepository.findBySystemUserLogin("admin")).thenReturn(Optional.of(entity));
        when(customerRepository.findByAppUserDetails(entity)).thenReturn(Optional.of(customerEntity));
        //when & then
        MvcResult result=  mockMvc.perform(MockMvcRequestBuilders.put("/customer/changePersonalInformation").with(httpBasic("admin","admin"))
                        .contentType(MediaType.APPLICATION_JSON).content(jsonBytes))
                    .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Assertions.assertEquals("Personal information data changed successfully.", result.getResponse().getContentAsString());
    }

    @Test
    void changePersonalInformationInvalidDataTest() throws Exception {
        //given
        AppUserDetailsEntity entity=  new AppUserDetailsEntity("admin","admin","adminMail",
                RolesList.ROLE_CUSTOMER.toString(),true,true,true,true);
        CustomerEntity customerEntity=new CustomerEntity(1L,"test_to_be_changed","test",
                "test","test","test","test",
                "test",new ArrayList<>(),new ArrayList<>(),entity);
        File jsonFile = new File("src/test/resources/testCustomerChangedInfoInvalid.json");
        byte[] jsonBytes = Files.readAllBytes(jsonFile.toPath());
        when(appUserDetailsRepository.findBySystemUserLogin("admin")).thenReturn(Optional.of(entity));
        when(customerRepository.findByAppUserDetails(entity)).thenReturn(Optional.of(customerEntity));
        //when & then
        MvcResult result=  mockMvc.perform(MockMvcRequestBuilders.put("/customer/changePersonalInformation").with(httpBasic("admin","admin"))
                        .contentType(MediaType.APPLICATION_JSON).content(jsonBytes))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Assertions.assertEquals("Fields are empty or missing. Check if all fields were mapped properly.", result.getResponse().getContentAsString());
    }



}

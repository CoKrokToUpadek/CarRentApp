package com.car_rent_app_gradle.controller;

import com.car_rent_app_gradle.client.enums.RolesList;
import com.car_rent_app_gradle.client.security_package.*;
import com.car_rent_app_gradle.domain.dto.TokenAndRoleDto;
import com.car_rent_app_gradle.domain.entity.AppUserDetailsEntity;
import com.car_rent_app_gradle.mapper.VehicleMapper;
import com.car_rent_app_gradle.repository.AppUserDetailsRepository;
import com.car_rent_app_gradle.repository.EmployeeRepository;
import com.car_rent_app_gradle.repository.VehicleRepository;
import com.car_rent_app_gradle.service.OpenEndPointsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(OpenEndPointsController.class)
@Import({OpenEndPointsService.class, VehicleMapper.class,TokenService.class,JWTConfig.class
, AppUserSpringSecurityDetailsService.class, AppUserDetailsService.class})
public class OpenEndpointControllerTestSuite {


    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper=new ObjectMapper();

    @MockBean
    VehicleRepository vehicleRepository;

    @MockBean
    EmployeeRepository employeeRepository;
    @MockBean
    AppUserSpringSecurityDetailsService service;

    @MockBean
    AppUserDetailsRepository appUserDetailsRepository;


    @TestConfiguration
    static class TestConfig {
     /*   I've disabled password encoding for tests to simplify adding temp user (I could probably generate
        BCrypt string add parse it to password field, but i think this is simpler and more readable*/
        @Bean
        public PasswordEncoder passwordEncoder() {
            return NoOpPasswordEncoder.getInstance();
        }
    }

    @BeforeEach
//ok
    void mockUserForTests(){
      AppUserDetailsEntity entity=  new AppUserDetailsEntity("admin","admin",
              RolesList.ROLE_ADMIN.toString(),true,true,true,true);
       when(service.loadUserByUsername("admin")).thenReturn(new AppUserDetails(entity));

    }

    @Test//ok
    void invalidBasicAuthClosedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/helloAuthorized").
                contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(401));
    }

    @Test//ok
    void validBasicAuthClosedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/helloAuthorized").with(httpBasic("admin","admin")).
                contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test//ok
    void validOpenTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test//ok
    void tokenGenerationTest() throws Exception {
       MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/login").with(httpBasic("admin","admin")))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        TokenAndRoleDto dto=mapper.readValue(result.getResponse().getContentAsString(),TokenAndRoleDto.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/helloAuthorized")
                        .header("Authorization","Bearer "+dto.getToken()))
               .andExpect(MockMvcResultMatchers.status().is(200));

    }

}

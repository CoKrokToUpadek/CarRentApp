package com.car_rent_app_gradle.controller;

import com.car_rent_app_gradle.client.enums.RolesList;
import com.car_rent_app_gradle.client.security_package.*;
import com.car_rent_app_gradle.domain.dto.TokenAndRoleDto;
import com.car_rent_app_gradle.domain.entity.AppUserDetailsEntity;
import com.car_rent_app_gradle.domain.entity.EmployeeEntity;
import com.car_rent_app_gradle.domain.entity.VehicleEntity;
import com.car_rent_app_gradle.mapper.AppUserDetailsMapper;
import com.car_rent_app_gradle.mapper.CustomerMapper;
import com.car_rent_app_gradle.mapper.VehicleMapper;
import com.car_rent_app_gradle.repository.AppUserDetailsRepository;
import com.car_rent_app_gradle.repository.CustomerRepository;
import com.car_rent_app_gradle.repository.EmployeeRepository;
import com.car_rent_app_gradle.repository.VehicleRepository;
import com.car_rent_app_gradle.service.CommonDataUserService;
import com.car_rent_app_gradle.service.OpenEndPointsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
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
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(OpenEndPointsController.class)
@Import({OpenEndPointsService.class, VehicleMapper.class,TokenService.class,JWTConfig.class
, AppUserSpringSecurityDetailsService.class, AppUserDetailsService.class, CustomerMapper.class
, AppUserDetailsMapper.class, CommonDataUserService.class})
@TestPropertySource("classpath:application-H2TestDb.properties")
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

    @MockBean
    CustomerRepository repository;


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
    @Test
    void tokenGenerationEmptyAuthenticationTest() throws Exception {
        MvcResult result=  mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().is(401)).andReturn();
        Assertions.assertEquals("Authentication data missing", result.getResponse().getContentAsString());
    }

    @Test
    void createCustomerAccountMissingInformationTest() throws Exception {
        File jsonFile = new File("src/test/resources/testCustomerInvalid.json");
        byte[] jsonBytes = Files.readAllBytes(jsonFile.toPath());
        MvcResult result=  mockMvc.perform(MockMvcRequestBuilders.post("/createCustomerAccount")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonBytes))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Assertions.assertEquals("Information provided in form was incomplete or invalid.", result.getResponse().getContentAsString());
    }

    @Test
    void createCustomerAccountValidTest() throws Exception {
        File jsonFile = new File("src/test/resources/testCustomerValid.json");
        byte[] jsonBytes = Files.readAllBytes(jsonFile.toPath());
        MvcResult result=  mockMvc.perform(MockMvcRequestBuilders.post("/createCustomerAccount")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonBytes))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Assertions.assertEquals("Customer was created successfully.", result.getResponse().getContentAsString());
    }


    @Test
    void getVehicleListValidTest() throws Exception {

        MvcResult result=  mockMvc.perform(MockMvcRequestBuilders.get("/getVehicleListForClients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Assertions.assertEquals("No vehicles available", result.getResponse().getContentAsString());
    }

    @Test
    void getVehicleListEmptyTest() throws Exception {
        VehicleEntity vehicle=new VehicleEntity("tempStatus",false,"testBrand",
                "testModel","testType","testCondition",
                100.00,"testPlateNumber",100);
        vehicle.setEmployeeThatRegisteredVehicle(new EmployeeEntity());
        when(vehicleRepository.findAllByVehicleNoLongerAvailable(false)).thenReturn(List.of(vehicle));

        MvcResult result=  mockMvc.perform(MockMvcRequestBuilders.get("/getVehicleListForClients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Assertions.assertEquals("[{\"id\":null,\"vehicleStatus\":\"tempStatus\",\"vehicleBrand\":\"testBrand\"," +
                "\"vehicleModel\":\"testModel\",\"vehicleType\":\"testType\",\"vehicleDailyPrice\":100.0}]",
                result.getResponse().getContentAsString());
    }

}

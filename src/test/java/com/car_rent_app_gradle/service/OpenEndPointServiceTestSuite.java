package com.car_rent_app_gradle.service;

import com.car_rent_app_gradle.domain.dto.TokenAndRoleDto;
import com.car_rent_app_gradle.repository.AppUserDetailsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class OpenEndPointServiceTestSuite {

    @Autowired
    OpenEndPointsService openEndPointsService;
    @Autowired
    AppUserDetailsRepository repository;

    @Test
    void generateTokenTest(){
        //given
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        UsernamePasswordAuthenticationToken loginAndPass=new UsernamePasswordAuthenticationToken("admin", "admin", authorities);
        //when
        TokenAndRoleDto dto= openEndPointsService.generateToken(loginAndPass);
        //then
        Assertions.assertEquals("ADMIN",dto.getRole());
        Assertions.assertFalse(dto.getToken().isEmpty());
    }
}

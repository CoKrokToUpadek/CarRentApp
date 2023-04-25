package com.car_rent_app_gradle.service;

import com.cokroktoupadek.carrentapp.domain.entity.CustomerEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerServiceTestSuite {
    @Autowired
    CustomerService customerService;

    @Test
    public void customerServiceTest(){
        CustomerEntity customer = new CustomerEntity("test","test","test","test","test","test","test");
        customerService.save(customer);
    }

}

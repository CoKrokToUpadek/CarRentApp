package com.car_rent_app_gradle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class CarRentAppGradleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentAppGradleApplication.class, args);
    }

}

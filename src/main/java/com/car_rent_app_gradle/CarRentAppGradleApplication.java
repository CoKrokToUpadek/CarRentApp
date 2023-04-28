package com.car_rent_app_gradle;

import com.car_rent_app_gradle.client.security_package.JWTKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(/*exclude = SecurityAutoConfiguration.class*/)
@EnableConfigurationProperties(JWTKeyProperties.class)
public class CarRentAppGradleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentAppGradleApplication.class, args);
    }

}

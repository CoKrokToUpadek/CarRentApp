package com.car_rent_app_gradle.client.components;

import com.car_rent_app_gradle.client.enums.RolesList;
import com.car_rent_app_gradle.domain.entity.AppUserDetailsEntity;
import com.car_rent_app_gradle.domain.entity.EmployeeEntity;
import com.car_rent_app_gradle.repository.AppUserDetailsRepository;
import com.car_rent_app_gradle.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Component
@Transactional
public class SuperUserCreator implements CommandLineRunner {

    AppUserDetailsRepository appUserDetailsRepository;
    EmployeeRepository employeeRepository;
    PasswordEncoder encoder;

    public SuperUserCreator(AppUserDetailsRepository appUserDetailsRepository, EmployeeRepository employeeRepository, PasswordEncoder encoder) {
        this.appUserDetailsRepository = appUserDetailsRepository;
        this.employeeRepository = employeeRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args){
        if (appUserDetailsRepository.count() == 0 && employeeRepository.count()==0) {
            AppUserDetailsEntity superUserDetailsEntity=new AppUserDetailsEntity("tempAdmin",encoder.encode("tempPassword"),"tempEmail",
                    RolesList.ROLE_ADMIN.toString(),true,true,true,true);
            EmployeeEntity superUserEmployeeEntity=new EmployeeEntity("0","temp","temp","temp",
                    "temp","temp","temp","temp", LocalDate.now(),0.00);

            appUserDetailsRepository.save(superUserDetailsEntity);
            AppUserDetailsEntity saved= appUserDetailsRepository.findBySystemUserLogin("tempAdmin").get();
            superUserEmployeeEntity.setAppUserDetails(saved);
            employeeRepository.save(superUserEmployeeEntity);
        }
    }
}

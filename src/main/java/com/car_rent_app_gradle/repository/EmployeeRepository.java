package com.car_rent_app_gradle.repository;

import com.car_rent_app_gradle.domain.entity.AppUserDetailsEntity;
import com.car_rent_app_gradle.domain.entity.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity,Long> {
    EmployeeEntity findByEmployeeId(Long employeeId);
    List<EmployeeEntity> findAll();
    EmployeeEntity findByAppUserDetails(AppUserDetailsEntity details);

}

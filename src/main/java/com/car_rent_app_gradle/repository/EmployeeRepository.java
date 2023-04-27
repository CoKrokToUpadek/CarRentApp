package com.car_rent_app_gradle.repository;

import com.car_rent_app_gradle.domain.entity.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity,Long> {
    EmployeeEntity findByEmployeeId(Long employeeId);

}

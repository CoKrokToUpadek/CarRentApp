package com.car_rent_app_gradle.repository;

import com.car_rent_app_gradle.domain.entity.CarAppUserDetailsEntity;
import org.springframework.data.repository.CrudRepository;

public interface CarAppUserDetailsRepository extends CrudRepository<CarAppUserDetailsEntity,Long> {
}

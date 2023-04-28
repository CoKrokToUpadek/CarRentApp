package com.car_rent_app_gradle.repository;

import com.car_rent_app_gradle.domain.entity.CarAppUserDetailsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CarAppUserDetailsRepository extends CrudRepository<CarAppUserDetailsEntity,Long> {
    Optional<CarAppUserDetailsEntity> findBySystemUserLogin(String login);
    List<CarAppUserDetailsEntity> findAll();

}

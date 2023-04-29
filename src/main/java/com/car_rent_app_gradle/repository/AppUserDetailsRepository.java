package com.car_rent_app_gradle.repository;

import com.car_rent_app_gradle.domain.entity.AppUserDetailsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AppUserDetailsRepository extends CrudRepository<AppUserDetailsEntity,Long> {
    Optional<AppUserDetailsEntity> findBySystemUserLogin(String login);
    List<AppUserDetailsEntity> findAll();

}

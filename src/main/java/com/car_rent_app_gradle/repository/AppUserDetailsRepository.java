package com.car_rent_app_gradle.repository;

import com.car_rent_app_gradle.domain.entity.AppUserDetailsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AppUserDetailsRepository extends CrudRepository<AppUserDetailsEntity,Long> {
    Optional<AppUserDetailsEntity> findBySystemUserLogin(String login);
    //added because I got stuck in createAccount Service method cause of mocks
    Optional<AppUserDetailsEntity> findBySystemUserLoginAndSystemUserEmail(String login,String email);
    Optional<AppUserDetailsEntity> findBySystemUserEmail(String email);

    List<AppUserDetailsEntity> findAll();

}

package com.car_rent_app_gradle.client.security_package;

import com.car_rent_app_gradle.domain.entity.AppUserDetailsEntity;
import com.car_rent_app_gradle.repository.AppUserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CarAppUserDetailsService {
    //Remember to make changes to this code each time its used
    AppUserDetailsRepository carAppUserDetailsRepository;

    @Autowired
    public CarAppUserDetailsService(AppUserDetailsRepository carAppUserDetailsRepository) {
        this.carAppUserDetailsRepository = carAppUserDetailsRepository;
    }

    public AppUserDetailsEntity save(AppUserDetailsEntity userEntity) {
        return carAppUserDetailsRepository.save(userEntity);
    }

    public Optional<AppUserDetailsEntity> findById(Long id) {
        return carAppUserDetailsRepository.findById(id);
    }

    public Optional<AppUserDetailsEntity> findByLogin(String login) {
        return carAppUserDetailsRepository.findBySystemUserLogin(login);
    }

    public List<AppUserDetailsEntity> findAll() {
        return carAppUserDetailsRepository.findAll();
    }


}

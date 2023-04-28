package com.car_rent_app_gradle.client.security_package;

import com.car_rent_app_gradle.domain.entity.CarAppUserDetailsEntity;
import com.car_rent_app_gradle.repository.CarAppUserDetailsRepository;
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
    CarAppUserDetailsRepository carAppUserDetailsRepository;

    @Autowired
    public CarAppUserDetailsService(CarAppUserDetailsRepository carAppUserDetailsRepository) {
        this.carAppUserDetailsRepository = carAppUserDetailsRepository;
    }

    public CarAppUserDetailsEntity save(CarAppUserDetailsEntity userEntity) {
        return carAppUserDetailsRepository.save(userEntity);
    }

    public Optional<CarAppUserDetailsEntity> findById(Long id) {
        return carAppUserDetailsRepository.findById(id);
    }

    public Optional<CarAppUserDetailsEntity> findByLogin(String login) {
        return carAppUserDetailsRepository.findBySystemUserLogin(login);
    }

    public List<CarAppUserDetailsEntity> findAll() {
        return carAppUserDetailsRepository.findAll();
    }


}

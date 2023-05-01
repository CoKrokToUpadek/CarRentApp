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
public class AppUserDetailsService {
    //Remember to make changes to this code each time its used
    AppUserDetailsRepository appUserDetailsRepository;

    @Autowired
    public AppUserDetailsService(AppUserDetailsRepository carAppUserDetailsRepository) {
        this.appUserDetailsRepository = carAppUserDetailsRepository;
    }

    public AppUserDetailsEntity save(AppUserDetailsEntity userEntity) {
        return appUserDetailsRepository.save(userEntity);
    }

    public Optional<AppUserDetailsEntity> findById(Long id) {
        return appUserDetailsRepository.findById(id);
    }

    public Optional<AppUserDetailsEntity> findByLogin(String login) {
        return appUserDetailsRepository.findBySystemUserLogin(login);
    }

    public List<AppUserDetailsEntity> findAll() {
        return appUserDetailsRepository.findAll();
    }


}

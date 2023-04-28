package com.car_rent_app_gradle.client.security_package;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private CarAppUserDetailsService carAppUserDetailsRepository;
    @Autowired
    public AppUserDetailsService(CarAppUserDetailsService carAppUserDetailsRepository) {
        this.carAppUserDetailsRepository = carAppUserDetailsRepository;
    }

    @Override
    public AppUserDetails loadUserByUsername(String username) {
        if (carAppUserDetailsRepository.findByLogin(username).isPresent()) {
            return new AppUserDetails(carAppUserDetailsRepository.findByLogin(username).get());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}

package com.car_rent_app_gradle.client.security_package;

import com.car_rent_app_gradle.domain.entity.AppUserDetailsEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AppUserDetails implements UserDetails {
    private final AppUserDetailsEntity carAppUserDetailsEntity;

    public AppUserDetails(AppUserDetailsEntity carAppUserDetailsEntity) {
        this.carAppUserDetailsEntity = carAppUserDetailsEntity;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority(carAppUserDetailsEntity.getSystemUserRole()));
    }
    @Override
    public String getPassword() {
        return carAppUserDetailsEntity.getSystemUserPassword();
    }

    @Override
    public String getUsername() {
        return carAppUserDetailsEntity.getSystemUserLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
      return   carAppUserDetailsEntity.getSystemUserAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return carAppUserDetailsEntity.getSystemUserAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return carAppUserDetailsEntity.getSystemUserCredentialsNonExpired();
    }
    @Override
    public boolean isEnabled() {
        return carAppUserDetailsEntity.getSystemUserAccountEnabled();
    }
}

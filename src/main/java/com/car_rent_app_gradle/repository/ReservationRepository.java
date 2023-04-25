package com.car_rent_app_gradle.repository;

import com.car_rent_app_gradle.domain.entity.ReservationEntity;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<ReservationEntity,Long> {


}

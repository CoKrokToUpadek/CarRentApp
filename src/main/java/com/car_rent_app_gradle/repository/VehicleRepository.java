package com.car_rent_app_gradle.repository;

import com.car_rent_app_gradle.domain.entity.VehicleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface VehicleRepository extends CrudRepository<VehicleEntity,Long> {

}

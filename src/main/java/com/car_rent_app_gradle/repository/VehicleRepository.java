package com.car_rent_app_gradle.repository;

import com.car_rent_app_gradle.domain.entity.VehicleEntity;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends CrudRepository<VehicleEntity,Long> {

    List<VehicleEntity> findAllByVehicleNoLongerAvailable(Boolean availability);
    Optional<VehicleEntity>  findByVehiclePlateNumber(String vehiclePlateNumber);

}

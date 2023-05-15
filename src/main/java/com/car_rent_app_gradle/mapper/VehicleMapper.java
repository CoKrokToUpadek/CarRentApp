package com.car_rent_app_gradle.mapper;

import com.car_rent_app_gradle.client.enums.VehicleStatusList;
import com.car_rent_app_gradle.domain.dto.VehicleForCustomersDto;
import com.car_rent_app_gradle.domain.dto.VehicleForEmployeesDto;
import com.car_rent_app_gradle.domain.entity.VehicleEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleMapper {
    public VehicleForCustomersDto mapToDtoForCustomers(final VehicleEntity vehicleEntity){
        return new VehicleForCustomersDto(vehicleEntity.getVehicleId(),vehicleEntity.getVehicleStatus(), vehicleEntity.getVehicleBrand(),
                vehicleEntity.getVehicleModel(), vehicleEntity.getVehicleType(), vehicleEntity.getVehicleDailyPrice());
    }

    public VehicleEntity mapFromVehicleForEmployeesToEntity(final VehicleForEmployeesDto vehicle){
        return new VehicleEntity(VehicleStatusList.ADDED_FOR_THE_FIRST_TIME.toString(),vehicle.getVehicleNoLongerAvailable(),
                vehicle.getVehicleBrand(), vehicle.getVehicleModel(), vehicle.getVehicleType(), vehicle.getVehicleCondition(),
                vehicle.getVehicleDailyPrice(), vehicle.getVehiclePlateNumber(),
                vehicle.getVehicleMillage());
    }

    public List<VehicleForCustomersDto> mapToDtoForCustomerList(List<VehicleEntity> vehicleEntityList){
        return vehicleEntityList.stream().map(this::mapToDtoForCustomers).collect(Collectors.toList());
    }
}

package com.car_rent_app_gradle.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Entity(name="vehicles")
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Long vehicleId;

    @NonNull
    @Column(name = "vehicle_is_available",columnDefinition = "BOOLEAN",nullable = false)
    private Boolean vehicleIsAvailable;

    @NonNull
    @Column(name = "vehicle_model",nullable = false)
    private String vehicleModel;

    @NonNull
    @Column(name = "vehicle_condition",nullable = false)
    private String vehicleCondition;

    @NonNull
    @Column(name = "vehicle_daily_price",nullable = false)
    private Double vehicleDailyPrice;

    @NonNull
    @Column(name = "vehicle_plate_number",nullable = false)
    private String vehiclePlateNumber;

    @NonNull
    @Column(name = "vehicle_millage",nullable = false)
    private Integer vehicleMillage;


    //conns
    @OneToMany(mappedBy = "vehicleChosenInReservation")
    private List<ReservationEntity> reservationsAssignedToVehicle;

    @OneToMany(mappedBy = "vehicleInvolvedInRent")
    private List<RentEntity> rentsInvolvedInVehicle;

    //registered employee
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_employee_registering", referencedColumnName = "employee_id",nullable = false)
    private EmployeeEntity EmployeeThatRegisteredVehicle;

}

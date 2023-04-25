package com.car_rent_app_gradle.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Entity(name="reservations")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @NonNull
    @Column(name = "reservation_pickup_date",nullable = false)
    @Temporal(TemporalType.DATE)
    private Date reservationPickupDate;

    @NonNull
    @Column(name = "reservation_pickup_location",nullable = false)
    private String reservationPickupLocation;

    @Column(name = "reservation_cancellation_details",nullable = true)
    private String reservationCancellationDetails;

    @NonNull
    @Column(name = "reservation_number_of_days",nullable = false)
    private Integer reservationNumberOfDays;

    @NonNull
    @Column(name = "reservation_reserve_date",columnDefinition = "DATE",nullable = false)
    private Date reservationReserveDate;

    @NonNull
    @Column(name = "reservation_return_date",columnDefinition = "DATE",nullable = false)
    private Date reservationReturnDate;

    //conns
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_customer_making_reservation", referencedColumnName = "customer_id",nullable = false)
    private CustomerEntity customerThatMadeReservation;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_employee_managing_reservation", referencedColumnName = "employee_id",nullable = false)
    private EmployeeEntity EmployeeThatManagesReservation;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_rent_contained_in_reservation", referencedColumnName = "rent_id",nullable = false)
    private RentEntity RentContainedInReservation;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_vehicle_chosen_in_reservation", referencedColumnName = "vehicle_id",nullable = false)
    private VehicleEntity vehicleChosenInReservation;



}

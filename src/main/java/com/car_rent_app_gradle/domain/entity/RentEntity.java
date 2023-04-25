package com.car_rent_app_gradle.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Entity(name="rents")
public class RentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_id")
    private Long rentId;

    @Column(name = "rent_refunded",columnDefinition = "BOOLEAN",nullable = true)
    private Boolean rentRefunded;

    @NonNull
    @Column(name = "rent_down_pay",nullable = false)
    private Double rentDownPay;

    @NonNull
    @Column(name = "rent_payment_method",nullable = false)
    private String rentPaymentMethod;

    @NonNull
    @Column(name = "rent_total_pay",nullable = false)
    private Double rentTotalPay;

    @NonNull
    @Column(name = "rent_payment_date", columnDefinition = "DATE",nullable = false)
    private LocalDate rentPaymentDate;

    @NonNull
    @Column(name = "rent_damage_compensation",nullable = false)
    private Double rentDamageCompensation;
    /* added because I feel like this field is need to control if transaction is closed or delayed*/
    @Column(name = "rent_payment_paid",columnDefinition = "BOOLEAN",nullable = true)
    private Boolean rent_payment_paid;

    //conns
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rent_customer_paying_rent", referencedColumnName = "customer_id",nullable = false)
    private CustomerEntity customerThatPaysForRent;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rent_reservation_contained_int_rent", referencedColumnName = "reservation_id",nullable = false)
    private ReservationEntity reservationContainedInRent;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rent_vehicle_involved_in_rent", referencedColumnName = "vehicle_id",nullable = false)
    private VehicleEntity vehicleInvolvedInRent;

}


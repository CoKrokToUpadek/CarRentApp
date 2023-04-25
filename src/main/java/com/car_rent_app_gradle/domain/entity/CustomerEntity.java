package com.car_rent_app_gradle.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Entity(name="customers")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;
    @NonNull
    @Column(name = "customer_first_name",nullable = false)
    private String customerFirstName;
    @NonNull
    @Column(name = "customer_last_name",nullable = false)
    private String customerLastName;
    @NonNull
    @Column(name = "customer_driving_license",nullable = false)
    private String customerDrivingLicense;
    @NonNull
    @Column(name = "customer_country",nullable = false)
    private String customerCountry;
    @NonNull
    @Column(name = "customer_city",nullable = false)
    private String customerCity;
    @NonNull
    @Column(name = "customer_street_and_house_no",nullable = false)
    private String customerStreetAndHouseNo;
    /* for mail and phone No*/
    @NonNull
    @Column(name = "customer_contact",nullable = false)
    private String customerContact;

    //spring security
    @NonNull
    @Column(name = "customer_login",nullable = false)
    private String customerLogin;
    @NonNull
    @Column(name = "customer_password",nullable = false)
    private String customerPassword;
    @NonNull
    @Column(name = "customer_role",nullable = false)
    private String customerPassword;
    @NonNull
    @Column(name = "customer_account_non_expired",columnDefinition = "BOOLEAN",nullable = false)
    private Boolean customerAccountNonExpired;
    @NonNull
    @Column(name = "customer_account_non_locked",columnDefinition = "BOOLEAN",nullable = false)
    private Boolean customerAccountNonLocked;
    @NonNull
    @Column(name = "customer_credentials_non_expired",columnDefinition = "BOOLEAN",nullable = false)
    private Boolean customerCredentialsNonExpired;
    @NonNull
    @Column(name = "customer_account_enabled",columnDefinition = "BOOLEAN",nullable = false)
    private Boolean customerAccountEnabled;

    //conns
    @OneToMany(mappedBy = "customerThatMadeReservation")
    private List<ReservationEntity> reservationsMadeByCustomer;

    @OneToMany(mappedBy = "customerThatPaysForRent")
    private List<RentEntity> rentsPaidByCustomer;
}

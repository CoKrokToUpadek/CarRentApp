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


    //conns
    @OneToMany(mappedBy = "customerThatMadeReservation")
    private List<ReservationEntity> reservationsMadeByCustomer;

    @OneToMany(mappedBy = "customerThatPaysForRent")
    private List<RentEntity> rentsPaidByCustomer;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_details_id", referencedColumnName = "system_user_id",nullable = false)
    private AppUserDetailsEntity carAppUserDetails;

}

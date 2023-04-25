package com.car_rent_app_gradle.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Entity(name="employees")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @NonNull
    @Column(name = "employee_first_name", nullable = false)
    private String employeeFirstName;
    @NonNull
    @Column(name = "employee_last_name", nullable = false)
    private String employeeLastName;
    @NonNull
    @Column(name = "employee_country",nullable = false)
    private String employeeCountry;
    @NonNull
    @Column(name = "employee_city",nullable = false)
    private String employeeCity;
    @NonNull
    @Column(name = "employee_street_and_house_no",nullable = false)
    private String employeeStreetAndHouseNo;

    /* for mail and phone No*/
    @NonNull
    @Column(name = "employee_contact",nullable = false)
    private String employeeContact;

    @Column(name = "employee_responsibilities",nullable = true)

    private String employeeResponsibilities;
    @NonNull
    @Column(name = "employee_joined_date",columnDefinition = "DATE",nullable = false)
    private Date employeeJoinedDate;

    @NonNull
    @Column(name = "employee_salary",nullable = false)
    private Double employeeSalary;


    //conns
    @OneToMany(mappedBy = "EmployeeThatRegisteredVehicle")
    List<VehicleEntity> vehiclesRegisteredToEmployee;

    @OneToMany(mappedBy = "EmployeeThatManagesReservation")
    List<ReservationEntity> reservationsManagedByEmployee;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_details_id", referencedColumnName = "system_user_id",nullable = false)
    private CarAppUserDetails carAppUserDetails;

}
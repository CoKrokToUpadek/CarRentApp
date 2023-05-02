package com.car_rent_app_gradle.domain.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Entity(name="system_users")
public class AppUserDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "system_user_id")
    private Long systemUserId;
    @NonNull
    @Column(name = "system_user_login",nullable = false)
    private String systemUserLogin;
    @NonNull
    @Column(name = "system_user_password",nullable = false)
    private String systemUserPassword;
    @NonNull
    @Column(name = "system_user_email",nullable = false)
    private String systemUserEmail;
    @NonNull
    @Column(name = "system_user_role",nullable = false)
    private String systemUserRole;
    @NonNull
    @Column(name = "system_user_account_non_expired",columnDefinition = "BOOLEAN",nullable = false)
    private Boolean systemUserAccountNonExpired;
    @NonNull
    @Column(name = "system_user_account_non_locked",columnDefinition = "BOOLEAN",nullable = false)
    private Boolean systemUserAccountNonLocked;
    @NonNull
    @Column(name = "system_user_credentials_non_expired",columnDefinition = "BOOLEAN",nullable = false)
    private Boolean systemUserCredentialsNonExpired;
    @NonNull
    @Column(name = "system_user_account_enabled",columnDefinition = "BOOLEAN",nullable = false)
    private Boolean systemUserAccountEnabled;

    @OneToOne(mappedBy = "carAppUserDetails")
    private EmployeeEntity systemUserEmployeeId;

    @OneToOne(mappedBy = "carAppUserDetails")
    private CustomerEntity systemUserCustomerId;

}

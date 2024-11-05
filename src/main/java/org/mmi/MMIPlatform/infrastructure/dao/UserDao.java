package org.mmi.MMIPlatform.infrastructure.dao;

import jakarta.persistence.*;
import lombok.*;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PermissionsEnum;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name="MMIPLATFORM_USER")
public class UserDao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "USER_ID")
    private String id;

    @Column(name = "MMIPLATFORM_USER_EMAIL")
    private String email;

    @Column(name="MMIPLATFORM_USER_PASSWORD")
    private String password;

    @Column(name = "MMIPLATFORM_USERNAME")
    private String username;

    @Column(name = "MMIPLATFORM_NAME")
    private String name;

    @Column(name = "MMIPLATFORM_FIST_NAME")
    private String firstName;

    @Column(name = "MMIPLATFORM_PHONE")
    private String phone;

    @Column(name = "MMIPLATFORM_ADDRESS")
    private String address;

    @Column(name = "MMIPLATFORM_CITY")
    private String city;

    @Column(name = "MMIPLATFORM_COUNTRY")
    private String country;

    @Column(name = "MMIPLATFORM_ESTABLISHMENT")
    private String establishment;

    @Column(name = "MMIPLATFORM_ACCESS")
    @Enumerated(EnumType.STRING)
    private PermissionsEnum access;


}

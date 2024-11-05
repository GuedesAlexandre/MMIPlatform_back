package org.mmi.MMIPlatform.infrastructure.dao;

import jakarta.persistence.*;
import lombok.*;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PermissionsEnum;

import java.util.UUID;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name="MMIPLATFORM_REFERENT")
public class ReferDao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "MMI_PLATFORM_REFER_ID")
    private UUID referId;

    @Column(name = "MMI_PLATFORM_REFER_EMAIL")
    private String referEmail;

    @Column(name = "MMI_PLATFORM_REFER_FIRSTNAME")
    private String referFirstName;

    @Column(name = "MMI_PLATFORM_REFER_LASTNAME")
    private String referLastName;

    @Column(name = "MMI_PLATFORM_REFER_PHONE")
    private String referPhone;

    @Column(name = "MMI_PLATFORM_REFER_PROMO")
    private String referPromo;

    @Column(name = "MMI_PLATFORM_REFER_CITY_ESTABLISMENT")
    private String referCityEstablishment;

    @Column(name = "MMI_PLATFORM_REFER_ACCESS")
    @Enumerated(EnumType.STRING)
    private PermissionsEnum referAccess;

}

package org.mmi.MMIPlatform.infrastructure.dao;


import jakarta.persistence.*;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.dao.enums.UEEnum;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name="MMIPLATFORM_STUDENT")
public class StudentDao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "MMI_PLATFORM_STUDENT_ID")
    private String id;

    @Column(name = "MMI_PLATFORM_STUDENT_LASTNAME")
    private String lastName;

    @Column(name = "MMI_PLATFORM_STUDENT_FIRSTNAME")
    private String firstName;

    @Column(name = "MMI_PLATFORM_STUDENT_PROMO")
    @Enumerated(EnumType.STRING)
    private PromoEnum promo;

    @Column(name = "MMI_PLATFORM_STUDENT_GROUP")
    private String group;

    @Column(name = "MMI_PLATFORM_STUDENT_NUM_ETU")
    private String num_etu;
}

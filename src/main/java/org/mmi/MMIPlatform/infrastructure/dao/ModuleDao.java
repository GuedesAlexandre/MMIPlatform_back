package org.mmi.MMIPlatform.infrastructure.dao;

import jakarta.persistence.*;
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
@Table(name="MMIPLATFORM_MODULE")
public class ModuleDao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "MMI_PLATFORM_MODULE_ID")
    private String id;

    @Column(name = "MMI_PLATFORM_MODULE_NAME")
    private String name;

    @Column(name = "MMI_PLATFORM_MODULE_PROMO")
    @Enumerated(EnumType.STRING)
    private PromoEnum promo;

    @Column(name = "MMI_PLATFORM_MODULE_SEMESTER")
    private int semester;

    @Column(name = "MMI_PLATFORM_MODULE_COEFF")
    private float coeff;

    @Column(name = "MMI_PLATFORM_MODULE_UE_NAME")
    @Enumerated(EnumType.STRING)
    private UEEnum ueName;
}

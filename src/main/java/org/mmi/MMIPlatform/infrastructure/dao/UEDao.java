package org.mmi.MMIPlatform.infrastructure.dao;

import jakarta.persistence.*;
import lombok.*;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.dao.enums.UEEnum;

import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name="MMIPLATFORM_UE")
public class UEDao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "MMI_PLATFORM_UE_ID")
    private String id;

    @Column(name = "MMI_PLATFORM_UE_NAME")
    @Enumerated(EnumType.STRING)
    private UEEnum name;

    @Column(name = "MMI_PLATFORM_UE_PROMO")
    @Enumerated(EnumType.STRING)
    private PromoEnum promo;

    @Column(name = "MMI_PLATFORM_UE_SEMESTER")
    private int semester;

    @Column(name = "MMI_PLATFORM_UE_SUM_NOTE")
    private double sum_note;

    @Column(name = "MMI_PLATEFORM_UE_COEFF")
    private float coeff;

    @OneToMany
    @JoinTable(name = "MMI_PLATFORM_UE_MODULE",
            joinColumns = @JoinColumn(name = "MMI_PLATFORM_UE_ID"),
            inverseJoinColumns = @JoinColumn(name = "MMI_PLATFORM_MODULE_ID"))
    private List<ModuleDao> module;


}
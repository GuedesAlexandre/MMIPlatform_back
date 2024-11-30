package org.mmi.MMIPlatform.infrastructure.dao;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private String promo;

    @Column(name = "MMI_PLATFORM_MODULE_SEMESTER")
    private String semester;

    @Column(name = "MMI_PLATFORM_MODULE_COEFF")
    private float coeff;

    @Column(name = "MMI_PLATFORM_MODULE_UENAME")
    private String ueName;

    @OneToMany(mappedBy = "module")
    @ToString.Exclude
    private List<NoteDao> notes;
}
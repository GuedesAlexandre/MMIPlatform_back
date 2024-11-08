package org.mmi.MMIPlatform.infrastructure.dao;

import jakarta.persistence.*;
import lombok.*;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;

import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name="MMIPLATFORM_MATRIX")
public class MatrixDao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "MMI_PLATFORM_MATRIX_ID")
    private String id;

    @Column(name = "MMI_PLATFORM_MATRIX_PROMO")
    @Enumerated(EnumType.STRING)
    private PromoEnum promo;

    @Column(name = "MMI_PLATFORM_MATRIX_SEMESTER")
    private int semester;

    @OneToOne
    @JoinTable(name = "MMI_PLATFORM_MATRIX_USER",
            joinColumns = @JoinColumn(name = "MMI_PLATFORM_MATRIX_ID"),
            inverseJoinColumns = @JoinColumn(name = "MMI_PLATFORM_USER_ID"))
    private UserDao user;
}
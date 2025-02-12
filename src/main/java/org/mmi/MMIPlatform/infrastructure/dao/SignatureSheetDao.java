package org.mmi.MMIPlatform.infrastructure.dao;

import jakarta.persistence.*;
import lombok.*;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name="MMIPLATFORM_SIGNATURE_SHEET")
public class SignatureSheetDao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "MMI_PLATFORM_USER_SIGNATURE_SHEET_ID")
    private String id;

    @Column(name = "MMI_PLATFORM_SIGNATURE_SHEET_PROMO")
    @Enumerated(EnumType.STRING)
    @NonNull
    private PromoEnum promo;

    @Column(name = "MMI_PLATFORM_SIGNATURE_SHEET_MODULE_NAME")
    @NonNull
    private String moduleName;

    @Column(name = "MMI_PLATFORM_SIGNATURE_SHEET_CREATED_AT")
    @NonNull
    private Date createdAt;

    @Column(name = "MMI_PLATFORM_SIGNATURE_SHEET_CREATED_FINISH_AT")
    @NonNull
    private Date finishAt;

    @OneToMany
    @JoinColumn(name="MMIPLATFORM_SIGNATURE_SHEET_SIGNATURE")
    @NonNull
    private List<SignatureDao> signatureDaos;

    @OneToMany
    @JoinTable(name = "MMI_PLATFORM_USER_STUDENT_SIGNATURE_SHEET",
            joinColumns = @JoinColumn(name = "MMI_PLATFORM_USER_STUDENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "MMI_PLATFORM_SIGNATURE_SHEET_ID"))
    @NonNull
    private List<StudentDao> studentDaos;
}

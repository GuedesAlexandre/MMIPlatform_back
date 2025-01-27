package org.mmi.MMIPlatform.infrastructure.dao;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name="MMIPLATFORM_SIGNATURE")
public class SignatureDao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "MMI_PLATFORM_SIGNATURE_ID")
    private String id;

    @Column(name = "MMI_PLATFORM_SIGNATURE_SIGN")
    private byte[] sign;

    @OneToOne
    @JoinColumn(name = "MMI_PLATFORM_SIGNATURE_USER_STUDENT")
    private UserStudentDao userStudentDao;

    @ManyToOne
    @JoinColumn(name="MMIPLATFORM_USER_SIGNATURE_SHEET")
    private SignatureSheetDao signatureSheetDao;
}

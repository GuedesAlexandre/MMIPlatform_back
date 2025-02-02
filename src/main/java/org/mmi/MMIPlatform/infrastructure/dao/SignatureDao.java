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

    @Lob
    @Column(name = "MMI_PLATFORM_SIGNATURE_SIGN")
    private byte @NonNull [] sign;

    @OneToOne
    @JoinColumn(name = "MMI_PLATFORM_SIGNATURE_USER_STUDENT")
    @NonNull
    private UserStudentDao userStudentDao;
}

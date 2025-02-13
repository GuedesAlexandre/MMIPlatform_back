package org.mmi.MMIPlatform.infrastructure.dao;

import jakarta.persistence.*;
import lombok.*;
import org.mmi.MMIPlatform.infrastructure.dao.enums.SignatureStatusEnum;

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
    private SignatureStatusEnum sign;

    @OneToOne
    @JoinColumn(name = "MMI_PLATFORM_SIGNATURE_STUDENT")
    private StudentDao studentDao;
}

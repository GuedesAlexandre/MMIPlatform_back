package org.mmi.MMIPlatform.infrastructure.dao;

import jakarta.persistence.*;
import lombok.*;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.dao.enums.TypeEnum;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name="MMIPLATFORM_INTERNSHIP")
public class InternshipDao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "MMI_PLATFORM_INTERNSHIP_ID")
    private String id;

    @Column(name = "MMI_PLATFORM_INTERNSHIP_TITLE")
    private String title;

    @Column(name = "MMI_PLATFORM_INTERNSHIP_COMMENT")
    private String comment;

    @Column(name = "MMI_PLATFORM_INTERNSHIP_WEEKNUMBER")
    private int weekNumber;

    @Column(name = "MMI_PLATFORM_INTERNSHIP_YEARS")
    private int years;

    @Column(name = "MMI_PLATFORM_INTERNSHIP_TYPE")
    @Enumerated(EnumType.STRING)
    private TypeEnum type;

    @ManyToOne
    @JoinColumn(name = "MMI_PLATFORM_STUDENT_ID")
    @ToString.Exclude
    private StudentDao student;
}

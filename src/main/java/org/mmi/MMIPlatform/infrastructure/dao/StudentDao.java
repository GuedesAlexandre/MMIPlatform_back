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
    private String numEtu;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoteDao> notes;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InternshipDao> internships;
}
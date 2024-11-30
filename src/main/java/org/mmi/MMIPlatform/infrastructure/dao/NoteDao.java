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
@Table(name="MMIPLATFORM_NOTE")
public class NoteDao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "MMI_PLATFORM_NOTE_ID")
    private String id;

    @Column(name = "MMI_PLATFORM_NOTE_COEFF")
    private float coeff;

    @Column(name = "MMI_PLATFORM_NOTE_NAME")
    private String name;

    @Column(name = "MMI_PLATFORM_NOTE_NOTE")
    private float note;

    @ManyToOne
    @JoinColumn(name = "MMI_PLATFORM_MODULE_ID")
    private ModuleDao module;

    @ManyToOne
    @JoinColumn(name = "MMI_PLATFORM_STUDENT_ID")
    private StudentDao student;
}
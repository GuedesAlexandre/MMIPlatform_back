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
@Table(name="MMIPLATFORM_USER_STUDENT")
public class UserStudentDao {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "MMI_PLATFORM_USER_STUDENT_ID")
    private String id;

    @Column(name = "MMI_PLATFORM_USER_STUDENT_NUM_ETU")
    @NonNull
    private String numEtu;

    @Column(name = "MMI_PLATFORM_USER_STUDENT_EMAIL")
    @NonNull
    private String email;

    @Column(name = "MMI_PLATFORM_USER_STUDENT_PASSWORD")
    @NonNull
    private String password;

    @Column(name = "MMI_PLATFORM_USER_STUDENT_LASTNAME")
    @NonNull
    private String lastName;

    @Column(name = "MMI_PLATFORM_USER_STUDENT_FIRSTNAME")
    @NonNull
    private String firstName;

    @Column(name = "MMI_PLATFORM_USER_STUDENT_CREATED_AT")
    private Date createdAt;
}

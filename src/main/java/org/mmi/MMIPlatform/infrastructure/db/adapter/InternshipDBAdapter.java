package org.mmi.MMIPlatform.infrastructure.db.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmi.MMIPlatform.infrastructure.dao.InternshipDao;
import org.mmi.MMIPlatform.infrastructure.dao.StudentDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.db.repository.InternshipDaoRepository;
import org.mmi.MMIPlatform.infrastructure.db.repository.StudentDaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InternshipDBAdapter {

    private final InternshipDaoRepository internshipDaoRepository;
    private final StudentDaoRepository studentDaoRepository;

    public List<StudentDao> getInternshipsByPromo(String promo) {
        return this.studentDaoRepository.findAll().stream().filter(student -> student.getPromo().equals(PromoEnum.valueOf(promo))).toList();
    }

    public InternshipDao postInternshipForAStudent(String numEtu, InternshipDao internship) {
        StudentDao student = this.studentDaoRepository.findByNumEtu(numEtu);
        if (student == null) {
            throw new IllegalArgumentException("student doesn't exist");
        }
        try {
            log.info("Adding internship for student {}", numEtu);
            internship.setStudent(student);
            this.internshipDaoRepository.save(internship);
            return internship;
        } catch (Exception e) {
            log.error("Error adding internship for a student {}: {}", numEtu, e.getMessage());
            throw e;
        }
    }
}

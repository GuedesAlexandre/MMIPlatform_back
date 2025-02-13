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
import java.util.UUID;

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
            throw new IllegalArgumentException("Student not found with numEtu: " + numEtu);
        }
            internship.setStudent(student);
            this.internshipDaoRepository.save(internship);
            return internship;
    }

    public String deleteInternshipByNumEtuYearsAndTitle(String numEtu, int years, String title) {
        StudentDao studentDao = this.studentDaoRepository.findByNumEtu(numEtu);

        if (studentDao == null) {
            throw new IllegalArgumentException("Student not found with numEtu: " + numEtu);
        }

        InternshipDao internshipDao = studentDao.getInternships().stream()
                .filter(internship -> internship.getTitle().equals(title) && internship.getYears() == years)
                .findFirst().orElseThrow((() -> new IllegalArgumentException("Enable to find internship for student: " + numEtu + " at years: " + years + " with title: " + title)));

        studentDao.getInternships().remove(internshipDao);
        this.internshipDaoRepository.delete(internshipDao);

        return "internship deleted successfully";
    }

    public InternshipDao putInternshipByNumEtuYearsAndTitle(String numEtu, int years, String title, InternshipDao newInternshipDao) {
        StudentDao studentDao = this.studentDaoRepository.findByNumEtu(numEtu);

        if (studentDao == null) {
            throw new IllegalArgumentException("Student not found with numEtu: " + numEtu);
        }

        InternshipDao internship = studentDao.getInternships().stream()
                .filter(i -> i.getTitle().equals(title) && i.getYears() == years)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Unable to find internship for student: " + numEtu + " at years: " + years + " with title: " + title));

        internship.setTitle(newInternshipDao.getTitle());
        internship.setComment(newInternshipDao.getComment());
        internship.setYears(newInternshipDao.getYears());
        internship.setType(newInternshipDao.getType());
        internship.setWeekCount(newInternshipDao.getWeekCount());

        this.internshipDaoRepository.save(internship);
        this.studentDaoRepository.save(studentDao);

        return internship;
    }
}

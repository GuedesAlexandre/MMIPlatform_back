package org.mmi.MMIPlatform.domain.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmi.MMIPlatform.domain.models.Internship;
import org.mmi.MMIPlatform.domain.models.Student;
import org.mmi.MMIPlatform.infrastructure.dao.InternshipDao;
import org.mmi.MMIPlatform.infrastructure.dao.StudentDao;
import org.mmi.MMIPlatform.infrastructure.db.adapter.InternshipDBAdapter;
import org.mmi.MMIPlatform.infrastructure.mapper.InternshipDaoMapper;
import org.mmi.MMIPlatform.infrastructure.mapper.StudentDaoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InternshipDomainService {

    private final InternshipDBAdapter internshipDBAdapter;
    private final InternshipDaoMapper internshipDaoMapper;
    private final StudentDaoMapper studentDaoMapper;

    public List<Student> getInternshipsByPromo(String promo) {
            List<StudentDao> studentDaoByPromo = this.internshipDBAdapter.getInternshipsByPromo(promo);
            return this.studentDaoMapper.studentsDaostoStudentsForInternship(studentDaoByPromo);
    }

    public Internship postInternshipForAStudent(String numEtu, Internship internship) {
        InternshipDao internshipDao = this.internshipDaoMapper.internshipToInternshipDao(internship);
        this.internshipDBAdapter.postInternshipForAStudent(numEtu, internshipDao);
        return this.internshipDaoMapper.internshipDaoToInternShip(internshipDao) ;}

    public String deleteInternshipByNumEtuYearsAndTitle(String numEtu, int years, String title) {
        return this.internshipDBAdapter.deleteInternshipByNumEtuYearsAndTitle(numEtu, years, title);
    }

    public Internship putInternshipByNumEtuYearsAndTitle(String numEtu, int years, String title, Internship internship) {
        InternshipDao internshipDao = this.internshipDaoMapper.internshipToInternshipDao(internship);
            this.internshipDBAdapter.putInternshipByNumEtuYearsAndTitle(numEtu, years, title, internshipDao);
        return this.internshipDaoMapper.internshipDaoToInternShip(internshipDao);
    }
}

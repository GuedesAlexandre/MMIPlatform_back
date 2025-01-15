package org.mmi.MMIPlatform.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.domain.models.Internship;
import org.mmi.MMIPlatform.infrastructure.dao.InternshipDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.TypeEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternshipDaoMapper {

    private final StudentDaoMapper studentDaoMapper;

    public Internship internshipDaoToInternShip(InternshipDao internshipDao) {
        return Internship.builder()
                .id(internshipDao.getId())
                .title(internshipDao.getTitle())
                .comment(internshipDao.getComment())
                .weekNumber(internshipDao.getWeekNumber())
                .type(String.valueOf(internshipDao.getType()))
                .student(studentDaoMapper.studentDaoToStudent(internshipDao.getStudent()))
                .build();
    }

    public List<Internship> internshipDaoListToInternshipList(List<InternshipDao> internshipDaoList) {
        return internshipDaoList.stream().map(this::internshipDaoToInternShip).toList();
    }

    public InternshipDao internshipToInternshipDao(Internship internship) {
        return InternshipDao.builder()
                .id(internship.getId())
                .title(internship.getTitle())
                .comment(internship.getComment())
                .weekNumber(internship.getWeekNumber())
                .type(TypeEnum.valueOf(internship.getType()))
                .student(studentDaoMapper.studentToStudentDao(internship.getStudent()))
                .build();
    }

    public List<InternshipDao> internshipListToInternshipDaoList(List<Internship> internshipList) {
        return internshipList.stream().map(this::internshipToInternshipDao).toList();
    }
}
package org.mmi.MMIPlatform.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.domain.models.Student;
import org.mmi.MMIPlatform.infrastructure.dao.StudentDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentDaoMapper {

    public Student studentDaoToStudent(StudentDao studentDao) {
        return Student.builder()
                .id(studentDao.getId())
                .lastName(studentDao.getLastName())
                .firstName(studentDao.getFirstName())
                .promo(studentDao.getPromo().toString())
                .group(studentDao.getGroup())
                .num_etu(studentDao.getNum_etu())
                .build();
    }

    public StudentDao studentToStudentDao(Student student) {
        return StudentDao.builder()
                .id(student.getId())
                .lastName(student.getLastName())
                .firstName(student.getFirstName())
                .promo(PromoEnum.valueOf(student.getPromo()))
                .build();
    }
}

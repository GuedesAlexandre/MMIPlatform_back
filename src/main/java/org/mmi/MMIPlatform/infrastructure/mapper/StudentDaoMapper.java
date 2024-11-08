package org.mmi.MMIPlatform.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.domain.models.Student;
import org.mmi.MMIPlatform.infrastructure.dao.StudentDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.git.dto.StudentDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentDaoMapper {

    public Student studentDaoToStudent(StudentDao studentDao) {
        if(studentDao == null) {
            return null;
        }
        return Student.builder()
                .id(studentDao.getId())
                .lastName(studentDao.getLastName())
                .firstName(studentDao.getFirstName())
                .promo(studentDao.getPromo().toString())
                .group(studentDao.getGroup())
                .numEtu(studentDao.getNumEtu())
                .build();
    }

    public StudentDao studentToStudentDao(Student student) {
        if(student == null) {
            return null;
        }
        return StudentDao.builder()
                .id(student.getId())
                .lastName(student.getLastName())
                .firstName(student.getFirstName())
                .promo(PromoEnum.valueOf(student.getPromo()))
                .group(student.getGroup())
                .numEtu(student.getNumEtu())
                .build();
    }
    public StudentDao studentDtoToStudentDao(StudentDto student) {
        if(student == null) {
            return null;
        }
        return StudentDao.builder()
                .lastName(student.getLastName())
                .firstName(student.getFirstName())
                .promo(PromoEnum.valueOf(student.getPromo()))
                .group(student.getGroup())
                .numEtu(student.getNumEtu())
                .build();
    }


}

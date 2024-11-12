package org.mmi.MMIPlatform.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.domain.models.Student;
import org.mmi.MMIPlatform.infrastructure.dao.StudentDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.git.dto.StudentDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentDaoMapper {

    private final ModuleDaoMapper moduleDaoMapper;

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
                .modules(null == studentDao.getModules() ? null : studentDao.getModules().stream().map(moduleDaoMapper::moduleDaoToModule).toList())
                .build();
    }

    public List<Student> studentsDaostoStudents(List<StudentDao> studentDaos){
        return studentDaos.stream().map(this::studentDaoToStudent).toList();
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
                .modules(null == student.getModules() ? null : student.getModules().stream().map(moduleDaoMapper::moduleToModuleDao).toList())
                .build();
    }

    public List<StudentDao> studentListToStudentDaoList(List<Student> students){
        return students.stream().map(this::studentToStudentDao).toList();
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

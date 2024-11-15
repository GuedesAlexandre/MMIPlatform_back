package org.mmi.MMIPlatform.application.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmi.MMIPlatform.application.dto.StudentDto;
import org.mmi.MMIPlatform.domain.mapper.StudentDtoMapper;
import org.mmi.MMIPlatform.domain.models.Student;
import org.mmi.MMIPlatform.domain.services.StudentDomaineService;
import org.mmi.MMIPlatform.infrastructure.dao.StudentDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentApplicationService {

    private final StudentDomaineService studentDomaineService;
    private final StudentDtoMapper studentDtoMapper;

    public List<StudentDto> getStudentsByPromo(String promo) {
        try {
            List<Student> studentsByPromo = this.studentDomaineService.getStudentsByPromo(promo);
            return studentDtoMapper.studentListToStudentDtoList(studentsByPromo);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public List<StudentDto> getAllStudents() {
        try {
            List<Student> students = studentDomaineService.getAllStudents();
            return studentDtoMapper.studentListToStudentDtoList(students);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}

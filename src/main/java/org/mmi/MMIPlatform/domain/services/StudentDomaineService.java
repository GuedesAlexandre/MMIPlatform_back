package org.mmi.MMIPlatform.domain.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmi.MMIPlatform.domain.models.Student;
import org.mmi.MMIPlatform.infrastructure.dao.StudentDao;
import org.mmi.MMIPlatform.infrastructure.db.adapter.StudentDBAdapter;
import org.mmi.MMIPlatform.infrastructure.mapper.StudentDaoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentDomaineService {

    private final StudentDBAdapter studentAdapter;
    private final StudentDaoMapper studentDaoMapper;

    public List<Student> getStudentsByPromo(String promo) {
        try {
            List<StudentDao> studentsDaoByPromo = studentAdapter.getStudentsByPromo(promo);
            return studentDaoMapper.studentsDaostoStudents(studentsDaoByPromo);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public List<Student> getAllStudents() {
        try {
            List<StudentDao> studentsDao = studentAdapter.getAllStudents();
            return studentDaoMapper.studentsDaostoStudents(studentsDao);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}


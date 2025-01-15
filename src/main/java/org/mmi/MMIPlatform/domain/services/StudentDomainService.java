package org.mmi.MMIPlatform.domain.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmi.MMIPlatform.domain.models.Note;
import org.mmi.MMIPlatform.domain.models.Student;
import org.mmi.MMIPlatform.infrastructure.dao.StudentDao;
import org.mmi.MMIPlatform.infrastructure.db.adapter.StudentDBAdapter;
import org.mmi.MMIPlatform.infrastructure.mapper.NoteDaoMapper;
import org.mmi.MMIPlatform.infrastructure.mapper.StudentDaoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentDomainService {

    private final StudentDBAdapter studentDBAdapter;
    private final StudentDaoMapper studentDaoMapper;
    private final NoteDaoMapper noteDaoMapper;

    public List<Student> getStudentsByPromo(String promo) {
        try {
            List<StudentDao> studentsDaoByPromo = studentDBAdapter.getStudentsByPromo(promo);
            return studentDaoMapper.studentsDaostoStudents(studentsDaoByPromo);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public String postNotesForAStudent(String numEtu, String ModuleName, Note note) {
        return studentDBAdapter.postNotesForAStudent(numEtu, ModuleName, this.noteDaoMapper.noteToNoteDao(note));
    }

    public String putNotesForAStudent(String numEtu, String ModuleName, String name, Note note) {
        return studentDBAdapter.putNotesForAStudent(numEtu, ModuleName, name, this.noteDaoMapper.noteToNoteDao(note));
    }

    public List<Student> getAllStudents() {
        try {
            return studentDaoMapper.studentsDaostoStudents( studentDBAdapter.getAllStudents());
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}


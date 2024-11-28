package org.mmi.MMIPlatform.infrastructure.db.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmi.MMIPlatform.infrastructure.dao.NoteDao;
import org.mmi.MMIPlatform.infrastructure.dao.StudentDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.db.repository.NoteDaoRepository;
import org.mmi.MMIPlatform.infrastructure.db.repository.StudentDaoRepository;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentDBAdapter {

    private final StudentDaoRepository studentDaoRepository;
    private final NoteDaoRepository noteDaoRepository;

    public List<StudentDao> getStudentsByPromo(String promo) {
        return this.studentDaoRepository.findAll().stream().filter(student -> student.getPromo().equals(PromoEnum.valueOf(promo))).toList();
    }

    public List<StudentDao> getAllStudents() {
        return this.studentDaoRepository.findAll();
    }

    public String postNotesForAStudent(String numEtu, String moduleName, NoteDao note) {
        StudentDao student = this.studentDaoRepository.findAll().stream().filter(s -> s.getNumEtu().equals(numEtu)).findFirst().orElse(null);
        if (student == null) {
            return "Student not found";
        }
        try {
            log.info("Adding note to module {} for student {}", moduleName, numEtu);

            // Save the note before adding it to the module
            this.noteDaoRepository.save(note);

            student.getModules().stream().filter(module -> module.getName().equals(moduleName)).findFirst().ifPresent(module -> module.getNotes().add(note));

            this.studentDaoRepository.save(student);
            return "Note added";
        } catch (Exception e) {
            return "Module not found";
        }
    }
}
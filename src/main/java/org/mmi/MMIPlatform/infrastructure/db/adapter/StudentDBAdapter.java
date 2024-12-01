package org.mmi.MMIPlatform.infrastructure.db.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.math.raw.Mod;
import org.mmi.MMIPlatform.infrastructure.dao.ModuleDao;
import org.mmi.MMIPlatform.infrastructure.dao.NoteDao;
import org.mmi.MMIPlatform.infrastructure.dao.StudentDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.db.repository.ModuleDaoRepository;
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
    private final ModuleDaoRepository moduleDaoRepository;

    public List<StudentDao> getStudentsByPromo(String promo) {
        return this.studentDaoRepository.findAll().stream().filter(student -> student.getPromo().equals(PromoEnum.valueOf(promo))).toList();
    }

    public List<StudentDao> getAllStudents() {
        return this.studentDaoRepository.findAll();
    }
    public String postNotesForAStudent(String numEtu, String moduleName, NoteDao note) {
        StudentDao student = this.studentDaoRepository.findByNumEtu(numEtu);
        List<ModuleDao> moduleDaoList = this.moduleDaoRepository.findAll().stream().filter(module -> module.getName().equals(moduleName)).toList();
        if (student == null) {
            return "Student not found";
        }
        if (moduleDaoList.isEmpty()) {
            return "Module not found";
        }
        try {
            log.info("Adding note to modules {} for student {}", moduleName, numEtu);
            for (ModuleDao moduleDao : moduleDaoList) {
                NoteDao newNote = new NoteDao();
                newNote.setCoeff(note.getCoeff());
                newNote.setName(note.getName());
                newNote.setNote(note.getNote());
                newNote.setModule(moduleDao);
                newNote.setStudent(student);
                moduleDao.getNotes().add(newNote);
                student.getNotes().add(newNote);
                this.noteDaoRepository.save(newNote);
            }
            this.moduleDaoRepository.saveAll(moduleDaoList);
            this.studentDaoRepository.save(student);
            return "Notes added for student: " + student;
        } catch (Exception e) {
            log.error("Error adding notes for student {}: {}", numEtu, e.getMessage());
            return "Error adding notes";
        }
    }
}
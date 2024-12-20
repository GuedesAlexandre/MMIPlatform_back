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
import java.util.Objects;

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
            throw new IllegalArgumentException("Student not found");
        }
        if (moduleDaoList.isEmpty()) {
            throw new IllegalArgumentException("Module not found");
        }
        if (note.getName() == null)  {
            throw new IllegalArgumentException("Control name not found");
        }
        if (note.getStatus() == null) {
            throw new IllegalArgumentException("Status not found");
        }
        try {
            log.info("Adding note to modules {} for student {}", moduleName, numEtu);
            for (ModuleDao moduleDao : moduleDaoList) {
                NoteDao newNote = new NoteDao();
                newNote.setCoeff(note.getCoeff());
                newNote.setName(note.getName());
                newNote.setNote(note.getNote());
                newNote.setStatus(note.getStatus());
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
            throw e;
        }
    }

    public String putNotesForAStudent(String numEtu, String moduleName, String name, NoteDao note) {
        StudentDao student = this.studentDaoRepository.findByNumEtu(numEtu);
        List<ModuleDao> moduleDaoList = this.moduleDaoRepository.findAll().stream().filter(module -> module.getName().equals(moduleName)).toList();
        if (student == null) {
            throw new IllegalArgumentException("Student not found");
        }
        if (moduleDaoList.isEmpty()) {
            throw new IllegalArgumentException("Module not found");
        }
        if (note.getName() == null)  {
            throw new IllegalArgumentException("Control name not found");
        }
        if (note.getStatus() == null) {
            throw new IllegalArgumentException("Status not found");
        }
        try {
            log.info("Changing note {} to modules {} for student {}", name, moduleName, numEtu);
            student.getNotes().stream().filter(s -> Objects.equals(s.getModule().getName(), moduleName) && Objects.equals(s.getName(), name)).forEach(
                    n -> {
                        n.setCoeff(note.getCoeff());
                        n.setName(note.getName());
                        n.setNote(note.getNote());
                        n.setStatus(note.getStatus());
                    }
            );
            moduleDaoList.forEach(m -> m.getNotes().stream().filter(n -> Objects.equals(n.getName(), name) && n.getStudent().getNumEtu().equals(numEtu)).forEach(
                    n -> {
                        n.setCoeff(note.getCoeff());
                        n.setName(note.getName());
                        n.setNote(note.getNote());
                        n.setStatus(note.getStatus());
                        this.noteDaoRepository.save(n);
                    }
            ));

            this.moduleDaoRepository.saveAll(moduleDaoList);
            this.studentDaoRepository.save(student);
            return "Notes changed for student: " + student;
        } catch (Exception e) {
            log.error("Error changing notes {} for student {}: {}", note, numEtu, e.getMessage());
            throw e;
        }
    }

}
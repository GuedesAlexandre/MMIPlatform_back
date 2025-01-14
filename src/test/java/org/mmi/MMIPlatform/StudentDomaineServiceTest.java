package org.mmi.MMIPlatform;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mmi.MMIPlatform.domain.models.Module;
import org.mmi.MMIPlatform.domain.models.Note;
import org.mmi.MMIPlatform.domain.models.Student;
import org.mmi.MMIPlatform.domain.services.StudentDomaineService;
import org.mmi.MMIPlatform.infrastructure.dao.ModuleDao;
import org.mmi.MMIPlatform.infrastructure.dao.NoteDao;
import org.mmi.MMIPlatform.infrastructure.dao.StudentDao;
import org.mmi.MMIPlatform.infrastructure.dao.UserDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.dao.enums.StatusEnum;
import org.mmi.MMIPlatform.infrastructure.db.adapter.StudentDBAdapter;
import org.mmi.MMIPlatform.infrastructure.db.repository.StudentDaoRepository;
import org.mmi.MMIPlatform.infrastructure.mapper.NoteDaoMapper;
import org.mmi.MMIPlatform.infrastructure.mapper.StudentDaoMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
public class StudentDomaineServiceTest {

    @Mock
    private StudentDBAdapter studentDBAdapter;

    @Mock
    private NoteDaoMapper noteDaoMapper;

    @Mock
    private StudentDaoMapper studentDaoMapper;

    @InjectMocks
    private StudentDomaineService studentDomaineService;

    @Mock
    private StudentDaoRepository studentDaoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPutNotesForAStudent() {
        String numEtu = "257812";
        String moduleName = "R.101 AUDIT Communication numérique";
        String name = "communication";
        Module module = Module.builder()
                .name("R.101 AUDIT Communication numérique")
                .promo("MMI01")
                .semester("1")
                .ueName("UE_COMPRENDRE")
                .coeff(8)
                .build();

        ModuleDao moduleDao = ModuleDao.builder()
                .name("R.101 AUDIT Communication numérique")
                .promo("MMI01")
                .semester("1")
                .ueName("UE_COMPRENDRE")
                .coeff(8)
                .build();

        Note note = Note.builder()
                .coeff(1)
                .name("comm")
                .note(19)
                .status("DONE")
                .module(module)
                .build();

        NoteDao noteDao = NoteDao.builder()
                .coeff(1)
                .name("comm")
                .note(19)
                .status(StatusEnum.valueOf("DONE"))
                .module(moduleDao)
                .build();
        StudentDao student = this.studentDaoRepository.findByNumEtu(numEtu);

        when(noteDaoMapper.noteToNoteDao(any(Note.class))).thenReturn(noteDao);
        when(studentDBAdapter.putNotesForAStudent(numEtu, moduleName, name, noteDao)).thenReturn("Notes changed for student: "+ student);
        when(noteDaoMapper.noteDaoToNote(any(NoteDao.class))).thenReturn(note);

        String result = studentDomaineService.putNotesForAStudent(numEtu, moduleName, name, note);

        assertNotNull(result);
        assertEquals("Notes changed for student: " + student, result);
    }

    @Test
    void testPostNotesForAStudent() {

        String numEtu = "257812";
        String ModuleName = "R.101 AUDIT Communication numérique";

        Module module = Module.builder()
                .name("R.101 AUDIT Communication numérique")
                .promo("MMI01")
                .semester("1")
                .ueName("UE_COMPRENDRE")
                .coeff(8)
                .build();

        ModuleDao moduleDao = ModuleDao.builder()
                .name("R.101 AUDIT Communication numérique")
                .promo("MMI01")
                .semester("1")
                .ueName("UE_COMPRENDRE")
                .coeff(8)
                .build();

        Note note = Note.builder()
                .coeff(1)
                .name("comm")
                .note(19)
                .status("DONE")
                .module(module)
                .build();

        NoteDao noteDao = NoteDao.builder()
                .coeff(1)
                .name("comm")
                .note(19)
                .status(StatusEnum.valueOf("DONE"))
                .module(moduleDao)
                .build();

        StudentDao student = this.studentDaoRepository.findByNumEtu(numEtu);

        when(noteDaoMapper.noteToNoteDao(any(Note.class))).thenReturn(noteDao);
        when(studentDBAdapter.postNotesForAStudent(numEtu, ModuleName, noteDao)).thenReturn("Note created successfully" + student);
        when(noteDaoMapper.noteDaoToNote(any(NoteDao.class))).thenReturn(note);

        String result = studentDomaineService.postNotesForAStudent(numEtu, ModuleName, note);

        assertNotNull(result);
        assertEquals("Note created successfully" + student, result);

    }

    @Test
    void testGetStudentsByPromo() {
    }
}

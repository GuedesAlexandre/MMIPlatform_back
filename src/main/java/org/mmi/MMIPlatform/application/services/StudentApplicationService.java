package org.mmi.MMIPlatform.application.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.mmi.MMIPlatform.application.dto.NoteDto;
import org.mmi.MMIPlatform.application.dto.StudentDto;
import org.mmi.MMIPlatform.domain.mapper.NoteDtoMapper;
import org.mmi.MMIPlatform.domain.mapper.StudentDtoMapper;
import org.mmi.MMIPlatform.domain.models.Student;
import org.mmi.MMIPlatform.domain.services.StudentDomaineService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentApplicationService {

    private final StudentDomaineService studentDomaineService;
    private final StudentDtoMapper studentDtoMapper;
    private final NoteDtoMapper noteDtoMapper;

    public List<StudentDto> getStudentsByPromo(String promo) {
        try {
            List<Student> studentsByPromo = this.studentDomaineService.getStudentsByPromo(promo);
            return studentDtoMapper.studentListToStudentDtoList(studentsByPromo);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public String postNotesForAStudent(String numEtu, String ModuleName, NoteDto note) {
        return studentDomaineService.postNotesForAStudent(numEtu, ModuleName, this.noteDtoMapper.noteDtoToNote(note));
    }

    public List<StudentDto> getAllStudents() {
        try {
            return studentDtoMapper.studentListToStudentDtoList( studentDomaineService.getAllStudents());
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}

package org.mmi.MMIPlatform.application.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmi.MMIPlatform.application.dto.NoteDto;
import org.mmi.MMIPlatform.application.dto.StudentDto;
import org.mmi.MMIPlatform.domain.mapper.NoteDtoMapper;
import org.mmi.MMIPlatform.domain.mapper.StudentDtoMapper;
import org.mmi.MMIPlatform.domain.models.Student;
import org.mmi.MMIPlatform.domain.services.StudentDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentApplicationService {

    private final StudentDomainService studentDomainService;
    private final StudentDtoMapper studentDtoMapper;
    private final NoteDtoMapper noteDtoMapper;

    public List<StudentDto> getStudentsByPromo(String promo) {
        try {
            return studentDtoMapper.studentListToStudentDtoList(this.studentDomainService.getStudentsByPromo(promo));
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public List<StudentDto> getStudentsByPromoAndGroup(String promo, String group) {
        try {
            return studentDtoMapper.studentListToStudentDtoList(this.studentDomainService.getStudentsByPromoAndGroup(promo, group));
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public StudentDto getStudentByNumEtu(String numEtu) {
        try {
            return studentDtoMapper.studentToStudentDto(this.studentDomainService.getStudentByNumEtu(numEtu));
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public String postNotesForAStudent(String numEtu, String ModuleName, NoteDto note) {
        return studentDomainService.postNotesForAStudent(numEtu, ModuleName, this.noteDtoMapper.noteDtoToNote(note));
    }

    public String putNotesForAStudent(String numEtu, String ModuleName, String name, NoteDto note) {
        return studentDomainService.putNotesForAStudent(numEtu, ModuleName, name, this.noteDtoMapper.noteDtoToNote(note));
    }

    public List<StudentDto> getAllStudents() {
        try {
            return studentDtoMapper.studentListToStudentDtoList(studentDomainService.getAllStudents());
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}

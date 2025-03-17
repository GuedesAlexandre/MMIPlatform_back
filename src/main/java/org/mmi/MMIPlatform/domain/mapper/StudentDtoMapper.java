package org.mmi.MMIPlatform.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.dto.StudentDto;
import org.mmi.MMIPlatform.domain.models.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentDtoMapper {

    private final ModuleDtoMapper moduleDtoMapper;
    private final NoteDtoMapper noteDtoMapper;
    private final InternshipDtoMapper internshipDtoMapper;


    public StudentDto studentToStudentDto(Student student){
        return StudentDto.builder()
                .lastName(student.getLastName())
                .firstName(student.getFirstName())
                .promo(student.getPromo())
                .group(student.getGroup())
                .numEtu(student.getNumEtu())
                .notes(this.noteDtoMapper.noteListToNoteDtoList(student.getNotes()))
                .tp(student.getTp())
                .email(student.getEmail())
                .build();
    }

    public StudentDto studentToStudentDtoForInternship(Student student){
        return StudentDto.builder()
                .lastName(student.getLastName())
                .firstName(student.getFirstName())
                .promo(student.getPromo())
                .group(student.getGroup())
                .numEtu(student.getNumEtu())
                .internships(this.internshipDtoMapper.internshipsToInternshipDto(student.getInternships()))
                .tp(student.getTp())
                .email(student.getEmail())
                .build();
    }

    public List<StudentDto> studentListToStudentDtoList(List<Student> students){
        return students.stream().map(this::studentToStudentDto).toList();
    }

    public List<StudentDto> studentListToStudentDtoListForInternship(List<Student> students){
        return students.stream().map(this::studentToStudentDtoForInternship).toList();
    }

    public Student studentDtoToStudent(StudentDto studentDto) {
        return Student.builder()
                .lastName(studentDto.getLastName())
                .firstName(studentDto.getFirstName())
                .promo(studentDto.getPromo())
                .group(studentDto.getGroup())
                .numEtu(studentDto.getNumEtu())
                .notes(this.noteDtoMapper.noteDtoListToNoteList(studentDto.getNotes()))
                .tp(studentDto.getTp())
                .email(studentDto.getEmail())
                .build();
    }

    public Student studentDtoToStudentForInternship(StudentDto studentDto) {
        return Student.builder()
                .lastName(studentDto.getLastName())
                .firstName(studentDto.getFirstName())
                .promo(studentDto.getPromo())
                .group(studentDto.getGroup())
                .numEtu(studentDto.getNumEtu())
                .internships(this.internshipDtoMapper.internshipDtoToInternship(studentDto.getInternships()))
                .tp(studentDto.getTp())
                .email(studentDto.getEmail())
                .build();
    }

    public List<Student> studentDtoListToStudent(List<StudentDto> studentDtos){
        return studentDtos.stream().map(this::studentDtoToStudent).toList();
    }
}

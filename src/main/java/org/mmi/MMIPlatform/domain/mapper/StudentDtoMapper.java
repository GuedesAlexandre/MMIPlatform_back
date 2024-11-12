package org.mmi.MMIPlatform.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.bouncycastle.math.raw.Mod;
import org.mmi.MMIPlatform.application.dto.StudentDto;
import org.mmi.MMIPlatform.domain.models.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentDtoMapper {

    private final ModuleDtoMapper moduleDtoMapper;



    public StudentDto studentToStudentDto(Student student){
        return StudentDto.builder()
                .lastName(student.getLastName())
                .firstName(student.getFirstName())
                .promo(student.getPromo())
                .group(student.getGroup())
                .numEtu(student.getNumEtu())
                .modules(this.moduleDtoMapper.moduleListToModuleDtoList(student.getModules()))
                .build();
    }

    public List<StudentDto> studentListToStudentDtoList(List<Student> students){
        return students.stream().map(this::studentToStudentDto).toList();
    }

    public Student studentDtoToStudent(StudentDto studentDto) {
        return Student.builder()
                .lastName(studentDto.getLastName())
                .firstName(studentDto.getFirstName())
                .promo(studentDto.getPromo())
                .group(studentDto.getGroup())
                .numEtu(studentDto.getNumEtu())
                .modules(this.moduleDtoMapper.moduleDtoListToModuleList(studentDto.getModules()))
                .build();
    }

    public List<Student> studentDtoListToStudent(List<StudentDto> studentDtos){
        return studentDtos.stream().map(this::studentDtoToStudent).toList();
    }
}

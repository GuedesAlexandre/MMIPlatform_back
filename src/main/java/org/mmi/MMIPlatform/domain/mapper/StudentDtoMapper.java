package org.mmi.MMIPlatform.domain.mapper;

import org.mmi.MMIPlatform.application.dto.StudentDto;
import org.mmi.MMIPlatform.domain.models.Student;
import org.springframework.stereotype.Service;

@Service
public class StudentDtoMapper {

    public StudentDto studentToStudentDto(Student student){
        return StudentDto.builder()
                .lastName(student.getLastName())
                .firstName(student.getFirstName())
                .promo(student.getPromo())
                .group(student.getGroup())
                .num_etu(student.getNum_etu())
                .build();
    }

    public Student studentDtoToStudent(StudentDto studentDto) {
        return Student.builder()
                .lastName(studentDto.getLastName())
                .firstName(studentDto.getFirstName())
                .promo(studentDto.getPromo())
                .group(studentDto.getGroup())
                .num_etu(studentDto.getNum_etu())
                .build();
    }
}

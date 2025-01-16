package org.mmi.MMIPlatform.application.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmi.MMIPlatform.application.dto.InternshipDto;
import org.mmi.MMIPlatform.application.dto.StudentDto;
import org.mmi.MMIPlatform.domain.mapper.InternshipDtoMapper;
import org.mmi.MMIPlatform.domain.mapper.StudentDtoMapper;
import org.mmi.MMIPlatform.domain.models.Internship;
import org.mmi.MMIPlatform.domain.models.Student;
import org.mmi.MMIPlatform.domain.services.InternshipDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InternshipApplicationService {

    private final InternshipDomainService internshipDomainService;
    private final InternshipDtoMapper internshipDtoMapper;
    private final StudentDtoMapper studentDtoMapper;

    public List<StudentDto> getInternshipsByPromo(String promo) {
        List<Student> studentsByPromoForInternship = this.internshipDomainService.getInternshipsByPromo(promo);
        return studentDtoMapper.studentListToStudentDtoListForInternship(studentsByPromoForInternship);
    }

    public InternshipDto postInternshipForAStudent(String numEtu, InternshipDto internshipDto) {
        Internship internship = internshipDtoMapper.internshipDtoToInternship(internshipDto);
        return this.internshipDtoMapper.internshipToInternshipDto(internshipDomainService.postInternshipForAStudent(numEtu, internship));
    }

    public String deleteInternshipByNumEtuYearsAndTitle(String numEtu, int years, String title) {
        return this.internshipDomainService.deleteInternshipByNumEtuYearsAndTitle(numEtu, years, title);
    }
}

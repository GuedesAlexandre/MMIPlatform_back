package org.mmi.MMIPlatform.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.dto.InternshipDto;
import org.mmi.MMIPlatform.domain.models.Internship;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InternshipDtoMapper {

    public InternshipDto internshipToInternshipDto(Internship internship) {
        return InternshipDto.builder()
                .title(internship.getTitle())
                .comment(internship.getComment())
                .weekNumber(internship.getWeekNumber())
                .type(internship.getType())
                .build();
    }

    public List<InternshipDto> internshipsToInternshipDto(List<Internship> internshipList) {
        return internshipList.stream().map(this::internshipToInternshipDto).toList();
    }

    public Internship internshipDtoToInternship(InternshipDto internshipDto) {
        return Internship.builder()
                .title(internshipDto.getTitle())
                .comment(internshipDto.getComment())
                .weekNumber(internshipDto.getWeekNumber())
                .type(internshipDto.getType())
                .build();
    }

    public List<Internship> internshipDtoToInternship(List<InternshipDto> internshipDtoList) {
        return internshipDtoList.stream().map(this::internshipDtoToInternship).toList();
    }
}
